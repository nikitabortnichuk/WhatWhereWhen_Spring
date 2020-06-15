package com.bortni.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Configuration
    @Order(1)
    public static class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final DataSource dataSource;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        public AdminConfigurationAdapter(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.dataSource = dataSource;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .anyRequest().hasAuthority("ADMIN")
                    .and()
                    .csrf().disable()
                    .formLogin()
                    .loginPage("/admin/sign-in")
                    .loginProcessingUrl("/admin/sign-in")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/admin/show-questions")
                    .failureUrl("/admin/sign-in?error=true")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .logoutSuccessUrl("/home")
                    .permitAll();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(bCryptPasswordEncoder)
                    .usersByUsernameQuery("select login as username, password, true as enabled from admin where login=?")
                    .authoritiesByUsernameQuery("select login as username, 'ADMIN' from admin where login=?");

        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
            web
                    .ignoring()
                    .antMatchers("/css/**", "/img/**", "/js/**", "/errors/**");
        }
    }

    @Configuration
    public static class UserConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final DataSource dataSource;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        public UserConfigurationAdapter(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.dataSource = dataSource;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/home", "/sign-in", "/sign-up").permitAll()
                    .antMatchers("/game", "/profile").hasAuthority("USER")
                    .antMatchers("/*")
                    .authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin()
                    .loginPage("/sign-in")
                    .defaultSuccessUrl("/profile")
                    .failureUrl("/sign-in?error=true")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home")
                    .permitAll();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(bCryptPasswordEncoder)
                    .usersByUsernameQuery("select username, password, true as enabled from users where username=?")
                    .authoritiesByUsernameQuery("select username, 'USER' from users where username=?");

        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
            web
                    .ignoring()
                    .antMatchers("/css/**", "/images/**", "/js/**", "/errors/**");
        }
    }

}
package com.bortni.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public static BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class UserConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final DataSource dataSource;

        @Autowired
        public UserConfigurationAdapter(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/game-www", "/home", "/sign-in", "/sign-up").permitAll()
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
                    .passwordEncoder(getBCryptPasswordEncoder())
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

    @Configuration
    @Order(2)
    public static class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final DataSource dataSource;

        @Autowired
        public AdminConfigurationAdapter(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/home", "/sign-in", "/sign-up", "/admin/sign-in").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/*")
                    .authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin()
                    .loginPage("/admin/sign-in")
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
                    .passwordEncoder(getBCryptPasswordEncoder())
                    .usersByUsernameQuery("select login, password, true as enabled from admin where login=?")
                    .authoritiesByUsernameQuery("select login, 'ADMIN' from admin where login=?");

        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);
            web
                    .ignoring()
                    .antMatchers("/css/**", "/img/**", "/js/**", "/errors/**");
        }
    }

}
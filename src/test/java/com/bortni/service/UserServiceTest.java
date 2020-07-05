package com.bortni.service;

import com.bortni.model.entity.User;
import com.bortni.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void shouldCallSave_WhenSave() {
        userService.save(getUser());

        verify(userRepository).save(any());
    }

    @Test
    public void shouldGetByUsername_WhenFindByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(getUser()));

        User expected = getUser();
        User actual = userService.findByUsername("anyUsername");

        assertEquals(expected, actual);
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .email("e")
                .password("p")
                .username("u")
                .build();
    }

}

package com.swe7303.devops.service.impl;

import com.swe7303.devops.model.User;
import com.swe7303.devops.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testUserLogin() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("pass123");

        when(userRepository.findByEmailAndPassword("test@test.com", "pass123")).thenReturn(user);

        User result = userService.userLogin("test@test.com", "pass123");

        assertNotNull(result);
        verify(userRepository, times(1)).findByEmailAndPassword("test@test.com", "pass123");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void testUserSignup() {
        User user = new User();
        user.setUsername("newuser");

        userService.userSignup(user);

        verify(userRepository, times(1)).save(user);
    }
}

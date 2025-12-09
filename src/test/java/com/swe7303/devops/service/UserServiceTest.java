package com.swe7303.devops.service;

import com.swe7303.devops.model.User;
import com.swe7303.devops.repository.UserRepository;
import com.swe7303.devops.service.impl.UserServiceImpl;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testUserLogin_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("pass123");
        user.setRole("CUSTOMER");

        when(userRepository.findByEmailAndPassword("test@example.com", "pass123")).thenReturn(user);

        User result = userService.userLogin("test@example.com", "pass123");

        assertNotNull(result);
        assertEquals("CUSTOMER", result.getRole());
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
        user.setUsername("testuser");

        userService.userSignup(user);

        verify(userRepository, times(1)).save(user);
    }
}

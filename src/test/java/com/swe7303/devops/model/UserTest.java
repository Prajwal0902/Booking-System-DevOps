package com.swe7303.devops.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();

        user.setId(1);
        user.setFname("John");
        user.setLname("Doe");
        user.setUsername("johndoe");
        user.setEmail("john@test.com");
        user.setPassword("pass123");
        user.setRole("CUSTOMER");

        assertEquals(1, user.getId());
        assertEquals("John", user.getFname());
        assertEquals("Doe", user.getLname());
        assertEquals("johndoe", user.getUsername());
        assertEquals("john@test.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals("CUSTOMER", user.getRole());
    }

    @Test
    void testUserCreation() {
        User user = new User();

        assertNotNull(user);
    }
}

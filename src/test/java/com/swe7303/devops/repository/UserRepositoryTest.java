package com.swe7303.devops.repository;

import com.swe7303.devops.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmailAndPassword() {
        User user = new User();
        user.setFname("John");
        user.setLname("Doe");
        user.setUsername("johndoe");
        user.setEmail("john@test.com");
        user.setPassword("pass123");
        user.setRole("CUSTOMER");

        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByEmailAndPassword("john@test.com", "pass123");

        assertNotNull(found);
        assertEquals("johndoe", found.getUsername());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setFname("Jane");
        user.setUsername("janedoe");
        user.setEmail("jane@test.com");
        user.setPassword("pass456");
        user.setRole("CUSTOMER");

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("jane@test.com", saved.getEmail());
    }
}

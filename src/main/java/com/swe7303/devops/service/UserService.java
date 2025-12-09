package com.swe7303.devops.service;

import com.swe7303.devops.model.User;
import java.util.List;

public interface UserService {
    void userSignup(User user);

    User userLogin(String email, String password);

    List<User> getAllUsers();

    User getUserById(Integer id);

    void deleteUser(Integer id);
}

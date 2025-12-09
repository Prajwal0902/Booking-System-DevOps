package com.swe7303.devops.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swe7303.devops.model.User;
import com.swe7303.devops.repository.UserRepository;
import com.swe7303.devops.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public void userSignup(User user) {
		userRepo.save(user);
	}

	@Override
	public User userLogin(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User getUserById(Integer id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public void deleteUser(Integer id) {
		userRepo.deleteById(id);
	}
}

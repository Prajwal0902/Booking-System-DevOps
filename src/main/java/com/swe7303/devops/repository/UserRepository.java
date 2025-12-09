package com.swe7303.devops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swe7303.devops.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
User findByEmailAndPassword(String email, String psw);
	
}


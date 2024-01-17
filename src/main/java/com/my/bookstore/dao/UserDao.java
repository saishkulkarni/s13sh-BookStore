package com.my.bookstore.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.bookstore.dto.User;
import com.my.bookstore.repository.UserRepository;

@Repository
public class UserDao {

	@Autowired
	UserRepository userRepository;

	public boolean checkEmailDuplicate(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean checkMobileDuplicate(long mobile) {
		return userRepository.existsByMobile(mobile);
	}

	public User save(User user) {
		return userRepository.save(user);
	}
}

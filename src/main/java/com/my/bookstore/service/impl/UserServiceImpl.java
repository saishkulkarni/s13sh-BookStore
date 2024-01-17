package com.my.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.my.bookstore.dao.UserDao;
import com.my.bookstore.dto.User;
import com.my.bookstore.helper.AES;
import com.my.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public String signup(User user, BindingResult result) {
		if (userDao.checkEmailDuplicate(user.getEmail()))
			result.rejectValue("email", "error.email", "Account Already Exists - Check Email");
		if (userDao.checkMobileDuplicate(user.getMobile()))
			result.rejectValue("mobile", "error.mobile", "Account Already Exists - Check Mobile");

		if (result.hasErrors()) {
			return "Signup";
		} else {
			user.setRole("USER");
			user.setPassword(AES.encrypt(user.getPassword(), "123"));
			userDao.save(user);
			return "redirect:/signin";
		}
	}

}

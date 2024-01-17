package com.my.bookstore.service;

import org.springframework.validation.BindingResult;

import com.my.bookstore.dto.User;

public interface UserService {

	public String signup(User user,BindingResult result);
}

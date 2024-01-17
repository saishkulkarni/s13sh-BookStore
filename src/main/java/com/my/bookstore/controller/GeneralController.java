package com.my.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.my.bookstore.dto.User;
import com.my.bookstore.service.UserService;

import jakarta.validation.Valid;

@Controller
public class GeneralController {

	@Autowired
	User user;

	@Autowired
	UserService userService;

	@GetMapping("/")
	public String loadHome() {
		return "Home";
	}

	@GetMapping("/signin")
	public String loadSignin() {
		return "Signin";
	}

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("user", user);
		return "Signup";
	}

	@PostMapping("/signup")
	public String signup(@Valid User user, BindingResult result) {
		if (result.hasErrors())
			return "Signup";
		else
			return userService.signup(user, result);
	}
}

package com.my.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/send-otp/{id}")
	public String sendOtp(@PathVariable int id, ModelMap map) {
		map.put("id", id);
		map.put("successMessage", "Otp Sent Success");
		return "EnterOtp";
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam int otp, @RequestParam int id, ModelMap map) {
		return userService.verifyOtp(id, otp, map);
	}
}

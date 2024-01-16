package com.my.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.bookstore.dto.User;

@Controller
public class GeneralController {

	@GetMapping("/")
	public String loadHome() {
		return "Home";
	}

	@GetMapping("/signin")
	public String loadSignin() {
		return "Signin";
	}

	@GetMapping("/signup")
	public String loadSignup() {
		return "Signup";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String signup(User user) {
		return user.toString();
	}
}

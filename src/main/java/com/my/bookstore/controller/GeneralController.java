package com.my.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

	@GetMapping("/")
	public String loadHome() {
		return "Home";
	}
	
	@GetMapping("/signin")
	public String loadSignin()
	{
		return "Signin";
	}
	
	@GetMapping("/signup")
	public String loadSignup()
	{
		return "Signup";
	}
}

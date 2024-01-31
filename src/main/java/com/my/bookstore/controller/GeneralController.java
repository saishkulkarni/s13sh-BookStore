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
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;
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
	public String verifyOtp(@RequestParam int otp, @RequestParam int id, ModelMap map, HttpSession session) {
		return userService.verifyOtp(id, otp, map, session);
	}

	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable int id, ModelMap map) {
		return userService.resendOtp(id, map);
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
		return userService.login(email, password, session);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.setAttribute("successMessage", "Logout Success");
		return "redirect:/";
	}

	@GetMapping("/books")
	public String loadBooks(HttpSession session, ModelMap map) {
		return userService.loadBooks(session, map);
	}

	@GetMapping("/buy-now/{id}")
	public String buyNow(@PathVariable int id, HttpSession session, ModelMap map) throws RazorpayException {
		return userService.buyNow(id, session, map);
	}

	@PostMapping("/confirm-order/{orderId}")
	public String confirmOrder(@PathVariable int orderId, @RequestParam String razorpay_payment_id,
			HttpSession session) {
		return userService.confirmOrder(orderId, razorpay_payment_id, session);
	}

	@GetMapping("/previous-orders")
	public String viewPreviousOrders(HttpSession session, ModelMap modelMap) {
		return userService.getPreviousOrders(session, modelMap);
	}

}

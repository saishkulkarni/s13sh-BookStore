package com.my.bookstore.service;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.my.bookstore.dto.User;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

public interface UserService {

	public String signup(User user,BindingResult result);

	public String verifyOtp(int id, int otp, ModelMap map, HttpSession session);

	public String resendOtp(int id, ModelMap map);

	public String login(String email, String password, HttpSession session);

	public String loadBooks(HttpSession session, ModelMap map);

	public String buyNow(int id, HttpSession session, ModelMap map) throws RazorpayException;

	public String confirmOrder(int orderId, String razorpay_payment_id, HttpSession session);
}

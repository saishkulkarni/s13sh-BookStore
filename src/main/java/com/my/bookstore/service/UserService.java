package com.my.bookstore.service;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.my.bookstore.dto.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

	public String signup(User user,BindingResult result);

	public String verifyOtp(int id, int otp, ModelMap map, HttpSession session);

	public String resendOtp(int id, ModelMap map);
}

package com.my.bookstore.service;

import org.springframework.ui.ModelMap;

import jakarta.servlet.http.HttpSession;

public interface AdminService {

	String loadAdminDashBoard(HttpSession session);

	String addBook(HttpSession session, ModelMap map);

}

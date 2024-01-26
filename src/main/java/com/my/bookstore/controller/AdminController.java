package com.my.bookstore.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.my.bookstore.dto.Book;
import com.my.bookstore.service.AdminService;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
@MultipartConfig
public class AdminController {

	@Autowired
	AdminService adminService;

	@GetMapping
	public String loadAdminDashboard(HttpSession session) {
		return adminService.loadAdminDashBoard(session);
	}

	@GetMapping("/add-book")
	public String addBook(HttpSession session, ModelMap map) {
		return adminService.addBook(session, map);
	}

	@PostMapping("/add-book")
	public String addBook(@Valid Book book, BindingResult result, @RequestParam MultipartFile photo,
			@RequestParam MultipartFile bookPdf, HttpSession session) throws IOException {
		if (result.hasErrors())
			return "AddBook";
		else
			return adminService.addBook(session, book, photo, bookPdf, result);
	}

	@GetMapping("/manage-books")
	public String displayBooks(HttpSession session, ModelMap map) {
		return adminService.displayBooks(session, map);
	}

	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id, HttpSession session) {
		return adminService.deleteBook(id, session);
	}
	
	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable int id, HttpSession session,ModelMap map) {
		return adminService.editBook(id, session,map);
	}
	
	@PostMapping("/update-book")
	public String updateBook(@Valid Book book, BindingResult result, @RequestParam MultipartFile photo,
			@RequestParam MultipartFile bookPdf, HttpSession session) throws IOException {
		if (result.hasErrors())
			return "EditBook";
		else
			return adminService.editBook(session, book, photo, bookPdf, result);
	}
}

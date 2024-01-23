package com.my.bookstore.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.my.bookstore.dao.BookDao;
import com.my.bookstore.dto.Book;
import com.my.bookstore.dto.User;
import com.my.bookstore.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	Book book;

	@Autowired
	BookDao bookDao;

	@Override
	public String loadAdminDashBoard(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN"))
				return "AdminDashboard";
			else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String addBook(HttpSession session, ModelMap map) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN")) {
				map.put("book", book);
				return "AddBook";
			} else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String addBook(HttpSession session, Book book, MultipartFile photo, MultipartFile bookPdf,
			BindingResult result) throws IOException {
		byte[] picture = new byte[photo.getInputStream().available()];
		photo.getInputStream().read(picture);
		System.out.println(picture.length);

		byte[] demoPdf = new byte[bookPdf.getInputStream().available()];
		bookPdf.getInputStream().read(demoPdf);

		System.out.println(demoPdf.length);

		book.setDemoPdf(demoPdf);
		book.setPicture(picture);

		bookDao.saveBook(book);

		session.setAttribute("successMessage", "Book Added Success");
		return "redirect:/admin";
	}

}

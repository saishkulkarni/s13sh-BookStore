package com.my.bookstore.service;

import java.io.IOException;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.my.bookstore.dto.Book;

import jakarta.servlet.http.HttpSession;

public interface AdminService {

	String loadAdminDashBoard(HttpSession session);

	String addBook(HttpSession session, ModelMap map);

	String addBook(HttpSession session, Book book, MultipartFile photo, MultipartFile bookPdf, BindingResult result) throws IOException;

}

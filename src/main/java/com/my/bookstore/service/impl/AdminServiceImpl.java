package com.my.bookstore.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

		bookDao.saveBook(book);

		book.setPicturePath("/images/" + book.getId() + ".jpg");
		book.setDemoPdfPath("/demoPdfs/" + book.getId() + ".pdf");

		String pdfFolderPath = "src/main/resources/static/demoPdfs";
		String pictureFolderPath = "src/main/resources/static/images";

		File file = new File(pictureFolderPath);
		if (!file.exists())
			file.mkdir();
		File file1 = new File(pdfFolderPath);
		if (!file1.exists())
			file1.mkdir();

		Path picturePath = Paths.get(pictureFolderPath, book.getId() + ".jpg");
		Path pdfPath = Paths.get(pdfFolderPath, book.getId() + ".pdf");

		Files.write(picturePath, photo.getBytes());
		Files.write(pdfPath, bookPdf.getBytes());

		bookDao.saveBook(book);
		session.setAttribute("successMessage", "Book Added Success");
		return "redirect:/admin";
	}

	@Override
	public String displayBooks(HttpSession session, ModelMap map) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN")) {
				List<Book> books = bookDao.fetchAllBooks();
				if (books.isEmpty()) {
					session.setAttribute("failMessage", "No Books Added Yet");
					return "redirect:/admin";
				} else {
					map.put("books", books);
					return "AdminViewBooks";
				}
			} else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String deleteBook(int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN")) {
				bookDao.delete(id);
				session.setAttribute("successMessage", "Book Deleted Success");
				return "redirect:/admin/manage-books";
			} else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String editBook(int id, HttpSession session, ModelMap map) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getRole().equals("ADMIN")) {
				Book book = bookDao.findById(id);
				map.put("book", book);
				return "EditBook";
			} else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
	}

	@Override
	public String editBook(HttpSession session, Book book, MultipartFile photo, MultipartFile bookPdf,
			BindingResult result) throws IOException {

		String pdfFolderPath = "src/main/resources/static/demoPdfs";
		String pictureFolderPath = "src/main/resources/static/images";

		book.setPicturePath("/images/" + book.getId() + ".jpg");
		book.setDemoPdfPath("/demoPdfs/" + book.getId() + ".pdf");

		File file = new File(pictureFolderPath);
		if (!file.exists())
			file.mkdir();
		File file1 = new File(pdfFolderPath);
		if (!file1.exists())
			file1.mkdir();

		Path picturePath = Paths.get(pictureFolderPath, book.getId() + ".jpg");
		Path pdfPath = Paths.get(pdfFolderPath, book.getId() + ".pdf");

		if (photo.getBytes().length != 0)
			Files.write(picturePath, photo.getBytes());
		if (bookPdf.getBytes().length != 0)
			Files.write(pdfPath, bookPdf.getBytes());

		bookDao.saveBook(book);
		session.setAttribute("successMessage", "Book Updated Success");
		return "redirect:/admin/manage-books";
	}

}

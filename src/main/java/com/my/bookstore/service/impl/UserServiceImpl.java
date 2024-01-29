package com.my.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.my.bookstore.dao.BookDao;
import com.my.bookstore.dao.UserDao;
import com.my.bookstore.dto.Book;
import com.my.bookstore.dto.BookOrder;
import com.my.bookstore.dto.User;
import com.my.bookstore.helper.AES;
import com.my.bookstore.helper.MailHelper;
import com.my.bookstore.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	MailHelper mailHelper;

	@Autowired
	BookDao bookDao;

	@Override
	public String signup(User user, BindingResult result) {
		if (userDao.checkEmailDuplicate(user.getEmail()))
			result.rejectValue("email", "error.email", "Account Already Exists - Check Email");
		if (userDao.checkMobileDuplicate(user.getMobile()))
			result.rejectValue("mobile", "error.mobile", "Account Already Exists - Check Mobile");

		if (result.hasErrors()) {
			return "Signup";
		} else {
			user.setRole("USER");
			user.setPassword(AES.encrypt(user.getPassword(), "123"));
			user.setOtp(new Random().nextInt(100000, 999999));
			userDao.save(user);
			// mailHelper.sendOtp(user);
			return "redirect:/send-otp/" + user.getId();
		}
	}

	@Override
	public String verifyOtp(int id, int otp, ModelMap map, HttpSession session) {
		User user = userDao.findById(id);
		if (user.getOtp() == otp) {
			user.setVerified(true);
			userDao.save(user);
			session.setAttribute("successMessage", "Account Created Successfully");
			return "redirect:/signin";
		} else {
			map.put("failMessage", "Invalid Otp, Try Again");
			map.put("id", id);
			return "EnterOtp";
		}
	}

	@Override
	public String resendOtp(int id, ModelMap map) {
		User user = userDao.findById(id);
		user.setOtp(new Random().nextInt(100000, 999999));
		userDao.save(user);
		// mailHelper.sendOtp(user);
		map.put("id", id);
		map.put("successMessage", "Otp Sent Again, Check Email");
		return "EnterOtp";
	}

	@Override
	public String login(String email, String password, HttpSession session) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			session.setAttribute("failMessage", "Invalid Email!!!");
			return "Signin";
		} else {
			if (AES.decrypt(user.getPassword(), "123").equals(password)) {
				session.setAttribute("user", user);
				session.setAttribute("successMessage", "Login Success");
				return "redirect:/";
			} else {
				session.setAttribute("failMessage", "Invalid Password!!!");
				return "Signin";
			}
		}
	}

	@Override
	public String loadBooks(HttpSession session, ModelMap map) {
		List<Book> books = bookDao.fetchAllBooks();
		if (books.isEmpty()) {
			session.setAttribute("failMessage", "No Books Present");
			return "redirect:/";
		} else {
			map.put("books", books);
			return "CustomerViewBooks";
		}
	}

	@Override
	public String buyNow(int id, HttpSession session, ModelMap map) throws RazorpayException {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Invalid Session");
			return "redirect:/login";
		} else {
			Book book = bookDao.findById(id);
			if (book.getStock() >= 1) {
				RazorpayClient razorpay = new RazorpayClient("rzp_test_71gEcjP0fsIjdi", "123F4USJtRwCeLden4tP7wpnYiF");
				JSONObject orderRequest = new JSONObject();
				orderRequest.put("amount", book.getPrice() * 100);
				orderRequest.put("currency", "INR");

				Order order = razorpay.orders.create(orderRequest);
				String orderId = order.get("id");
				BookOrder bookOrder = new BookOrder();
				bookOrder.setAmount(book.getPrice());
				bookOrder.setCurrency("INR");
				bookOrder.setOrderId(orderId);
				bookOrder.setBook(book);

				if (user.getBookOrders() == null)
					user.setBookOrders(new ArrayList<BookOrder>());

				user.getBookOrders().add(bookOrder);
				userDao.save(user);
				session.setAttribute("user", user);
				map.put("order", bookOrder);
				map.put("key", "rzp_test_71gEcjP0fsIjdi");
				map.put("user", user);
				map.put("book", book);
				return "ConfirmOrder";

			} else {
				session.setAttribute("failMessage", "Out of Stock");
				return "redirect:/";
			}
		}

	}
}

package com.my.bookstore.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.bookstore.dto.Book;
import com.my.bookstore.dto.BookOrder;
import com.my.bookstore.repository.BookOrderRepository;
import com.my.bookstore.repository.BookRepository;

@Repository
public class BookDao {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookOrderRepository bookOrderRepository;

	public void saveBook(Book book) {
		bookRepository.save(book);
	}

	public List<Book> fetchAllBooks() {
		return bookRepository.findAll();
	}

	public void delete(int id) {
		bookRepository.deleteById(id);
	}

	public Book findById(int id) {
		return bookRepository.findById(id).orElseThrow();
	}

	public BookOrder findOrderById(int id) {
		return bookOrderRepository.findById(id).orElseThrow();
	}

	public void saveBookOrder(BookOrder bookOrder) {
		bookOrderRepository.save(bookOrder);
	}

}

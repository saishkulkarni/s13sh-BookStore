package com.my.bookstore.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.bookstore.dto.Book;
import com.my.bookstore.repository.BookRepository;

@Repository
public class BookDao {

	@Autowired
	BookRepository bookRepository;

	public void saveBook(Book book) {
		bookRepository.save(book);
	}

}

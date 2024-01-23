package com.my.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.bookstore.dto.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}

package com.my.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.bookstore.dto.BookOrder;

public interface BookOrderRepository extends JpaRepository<BookOrder, Integer> {

}

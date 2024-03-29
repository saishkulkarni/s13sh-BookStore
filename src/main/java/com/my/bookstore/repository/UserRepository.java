package com.my.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.bookstore.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	User findByEmail(String email);

}

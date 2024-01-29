package com.my.bookstore.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Component
public class BookOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String orderId;
	String currency;
	double amount;
	String paymentId;

	@ManyToOne
	Book book;

}

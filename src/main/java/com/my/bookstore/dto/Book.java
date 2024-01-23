package com.my.bookstore.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Component
@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 5, max = 50, message = "* Enter Between 5 ~ 50 letters")
	private String name;
	@DecimalMin(value = "1", message = "* Enter Proper Price")
	private double price;
	@Size(min = 3, max = 10, message = "* Enter Between 3 ~ 10 letters")
	private String genre;
	@DecimalMin(value = "1", message = "* Enter Stock greater than 1")
	private int stock;
	@NotEmpty(message = "* this is required field")
	private String type;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] picture;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] demoPdf;
}

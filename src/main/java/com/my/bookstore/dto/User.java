package com.my.bookstore.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Component
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "* this is Required Field")
	@Size(min = 3, max = 10, message = "* Enter Between 3 ~ 10 letters")
	private String name;
	@Email
	@NotEmpty(message = "* this is Required Field")
	private String email;
	@NotNull(message = "* this is Required Field")
	@DecimalMin(value = "6000000000", message = "* Enter Proper Mobile Number")
	@DecimalMax(value = "9999999999", message = "* Enter Proper Mobile Number")
	private long mobile;
	@NotEmpty(message = "* this is Required Field")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$", message = "* Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character, no space, and it must be 8-16 characters long.")
	private String password;
	@NotEmpty(message = "* this is Required Field")
	private String gender;
	@NotNull(message = "* this is Required Field")
	@Past(message = "* Enter Valid Date")
	private LocalDate dob;
	private String role;
	private int otp;
	private boolean verified;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<BookOrder> bookOrders;
}

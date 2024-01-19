package com.my.bookstore.helper;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.my.bookstore.dto.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailHelper {

	@Autowired
	JavaMailSender mailSender;

	public void sendOtp(User user) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String gen = "";
		if (user.getGender().equalsIgnoreCase("male"))
			gen = "Mr. ";
		else
			gen = "Ms. ";
		
		String body = "<html><body><h1>Hello " + gen + user.getName() + ",</h1><h2>Your One Time Password is: "
				+ user.getOtp() + "</h2><h3>Thanks and Regards</h3></body></html>";
		
		try {
			helper.setFrom("saishkulkarni7@gmail.com", "BooksCart");
			helper.setTo(user.getEmail());
			helper.setSubject(user.getName() + ", YOur OTP is Inside");
			helper.setText(body, true);
			
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(message);
	}

}

package com.ConnectMate;

import static org.assertj.core.api.Assertions.assertThat;

import com.ConnectMate.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private EmailService emailService;

	@Test
	void sendEmailTest() {
		System.out.println("Email sending");
		emailService.sendEmail("test@Gmail.com", "Email from ConnectMate Application", "Test");
	}

	@Test
	void sendEmailTest2() {
		String[] emails = {"test1@Gmail.com","test2@Gmail.com"};
		String htmlBody = "<html><body><h1>This is a test email</h1></body></html>";
		String subject = "HTML Body";
		emailService.sendEmailWithHTML(emails,subject,htmlBody);
	}
//
//	@Autowired
//	private EmailService service;
//
//
//	void sendEmailTest() {
//		service.sendEmail("batchlcwd@gmail.com", "Just managing the emails",
//				"this is scm project working on email service");
//	}
//
//
//	void testUnits() {
//
//
//		int result=40;
//
//		 List<String>  list = List.of("ram","shyam","ankit");
//
////		assertThat(result).isEqualTo(50);
//
//		 assertThat(list).asList().size().isGreaterThan(5);
//
//
//
//
//	}

}

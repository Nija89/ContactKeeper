package com.hibernateJpa.ContactApp;

import com.hibernateJpa.ContactApp.Controller.UserController;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactApplication {

	private final UserController userController;

	public ContactApplication(UserController theUserController){
		this.userController = theUserController;
	}

	public static void main(String[] args) {
		SpringApplication.run(ContactApplication.class, args);
	}

	@PostConstruct
	public void init(){
		userController.mainController();
	}

}
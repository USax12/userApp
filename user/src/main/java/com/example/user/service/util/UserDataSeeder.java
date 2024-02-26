package com.example.user.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;

@Component
public class UserDataSeeder implements CommandLineRunner {

	private final UserRepository userRepository;

	@Autowired
	public UserDataSeeder(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// Check if data already exists
		if (userRepository.count() == 0) {

			User user1 = new User();
			user1.setUsername("Utkarsh5");
			user1.setEmail("utkarsh.saxena@example.com");
			user1.setMobileNumber("1234567890"); // Set mobile number
			userRepository.save(user1);

			User user2 = new User();
			user2.setUsername("Utkarsh");
			user2.setEmail("utkarsh.singh@example.com");
			user2.setMobileNumber("9876543210"); // Set mobile number
			userRepository.save(user2);

		}
	}
}

package com.example.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.common.exception.CustomException.DuplicateUserException;
import com.example.common.exception.CustomException.UserNotFoundException;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(User newUser) {
		// Check if the username, email, or mobile number is already taken
		if (userRepository.existsByUsername(newUser.getUsername()) || userRepository.existsByEmail(newUser.getEmail())
				|| userRepository.existsByMobileNumber(newUser.getMobileNumber())) {
			throw new DuplicateUserException(
					"Username, email, or mobile number is already taken. Please choose different ones.");
		}

		// Save the new user if the username, email, and mobile number are not taken
		return userRepository.save(newUser);
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
	}

}
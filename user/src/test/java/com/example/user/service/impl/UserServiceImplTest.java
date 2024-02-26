package com.example.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.common.exception.CustomException.DuplicateUserException;
import com.example.common.exception.CustomException.UserNotFoundException;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;

@SpringJUnitConfig
@SpringBootTest
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Test
	void registerUser_WithNewUser_ShouldReturnSavedUser() {

		User newUser = new User(1L, "newUser", "newuser@example.com", "1234567890");

		// Mock the userRepository behavior
		Mockito.when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
		Mockito.when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
		Mockito.when(userRepository.existsByMobileNumber(newUser.getMobileNumber())).thenReturn(false);
		Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

		User savedUser = userService.registerUser(newUser);

		// Assert
		assertEquals(newUser, savedUser);
	}

	@Test
	void registerUser_WithDuplicateUsername_ShouldThrowDuplicateUserException() {

		User newUser = new User(1L, "existingUser", "newuser@example.com", "1234567890");

		// Mock the userRepository behavior
		Mockito.when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(true);

		assertThrows(DuplicateUserException.class, () -> userService.registerUser(newUser));
	}

	@Test
	void getUserById_WithValidUserId_ShouldReturnUser() {
		// Arrange
		Long userId = 1L;
		User existingUser = new User(userId, "existingUser", "existinguser@example.com", "9876543210");

		// Mock the userRepository behavior
		Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));

		User foundUser = userService.getUserById(userId);

		// Assert
		assertEquals(existingUser, foundUser);
	}

	@Test
	void getUserById_WithInvalidUserId_ShouldThrowUserNotFoundException() {

		Long invalidUserId = 999L;

		// Mock the userRepository behavior
		Mockito.when(userRepository.findById(invalidUserId)).thenReturn(java.util.Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUserById(invalidUserId));
	}

}

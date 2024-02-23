package com.example.user.service;

import com.example.user.model.User;

public interface UserService {

	User registerUser(User newUser);

	User getUserById(Long userId);

}

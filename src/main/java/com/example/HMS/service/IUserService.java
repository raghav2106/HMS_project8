package com.example.HMS.service;

import java.util.Optional;

import com.example.HMS.entity.User;

public interface IUserService {

	
	Long saveUser(User user);
	
	Optional<User> findByUsername(String username);
	
	void updateUserPwd(String pwd,Long userId);
	
}

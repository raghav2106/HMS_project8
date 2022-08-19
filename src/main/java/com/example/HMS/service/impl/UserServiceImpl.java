package com.example.HMS.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HMS.entity.User;
import com.example.HMS.repo.UserRepository;
import com.example.HMS.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService  {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repo;
	
	
	@Override
	public Long saveUser(User user) {
		//read generated pwd
				String pwd = user.getPassword();
				//enode password
				String encPwd = passwordEncoder.encode(pwd);
				//set back to object
				user.setPassword(encPwd);
		return repo.save(user).getId();
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return repo.findByUsername(username);
	}


	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Optional<User> opt = findByUsername(username);
		if(!opt.isPresent()) 
			throw new UsernameNotFoundException(username);
		else {
			User user = opt.get();
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(), 
					user.getPassword(), 
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
					);
		}
	}
	
	@Transactional
	public void updateUserPwd(String pwd, Long userId) {
		String encPwd = passwordEncoder.encode(pwd);
		repo.updateUserPwd(encPwd, userId);
	}
	
}

package com.example.HMS.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.HMS.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	@Modifying
	@Query("UPDATE User SET password=:encPwd WHERE id=:userId")
	void updateUserPwd(String encPwd,Long userId);
	
}

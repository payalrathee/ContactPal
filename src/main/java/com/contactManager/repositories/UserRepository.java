package com.contactManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManager.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User getUserByUsername(String username);
//	public User getUserByUsernameAndPassword(String username, String password);
	public User getUserByEmail(String email);
}

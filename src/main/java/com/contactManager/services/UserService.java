package com.contactManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactManager.models.User;
import com.contactManager.repositories.UserRepository;
import com.contactManager.utilities.Utility;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	public User registerUser(User user) {
		
		// set additional info
		user.setUserRole("ROLE_USER");
		user.setStatus(true);
		
		// save user in session
		Utility.session().setAttribute("currentUser", user);
		
		return userRepo.save(user);
	}
}

package com.contactManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactManager.models.User;
import com.contactManager.repositories.UserRepository;
import com.contactManager.utilities.Utility;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepo;

	public boolean authenticateUser(String username, String password) {
		
		User user = userRepo.getUserByUsernameAndPassword(username, password);
		
		if(user == null) {
			return false;
		} else {
			
			// save user into session
			Utility.session().setAttribute("currentUser", user);
			
			return true;
		}
		
	}
}

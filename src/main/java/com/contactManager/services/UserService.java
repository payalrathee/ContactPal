package com.contactManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contactManager.models.User;
import com.contactManager.repositories.UserRepository;
@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public User registerUser(User user){
		
		// set additional info
		user.setUserRole("ROLE_USER");
		user.setActive(true);
		
		// encrypt password
		user.setPassword(encoder.encode(user.getPassword()));
		
		// save user
		user = userRepo.save(user);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails user = userRepo.getUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return user;
	}

}

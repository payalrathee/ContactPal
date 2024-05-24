package com.contactManager.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repositories.UserRepository;
@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired 
	private FileHandlerService fileService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails user = userRepo.getUserByUsername(username);
		if(user == null) {
			user = userRepo.getUserByEmail(username);
		}
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return user;
	}
	
	public User registerUser(User user) throws Exception{
		
		if(userRepo.getUserByUsername(user.getUsername()) != null) {
			throw new Exception("UsernameAlreadyExists");
		}
		
		if(userRepo.getUserByEmail(user.getEmail()) != null) {
			throw new Exception("EmailAlreadyExists");
		}
		
		// set additional info
		user.setUserRole("ROLE_USER");
		user.setActive(true);
		
		// encrypt password
		user.setPassword(encoder.encode(user.getPassword()));
		
		// save user
		user = userRepo.save(user);
		
		return user;
	}
	
	public User updateUser(User user) {
		
		User savedUser = userRepo.findById(user.getId()).get();
		
		savedUser.setFname(user.getFname());
		savedUser.setLname(user.getLname());
		savedUser.setPhone(user.getPhone());
		savedUser.setAbout(user.getAbout());
		
		userRepo.save(savedUser);
		return savedUser;
	}
	
	public User deleteUser(User user) throws Exception {

		deleteProfileImage(user);
		userRepo.delete(user);
		return user;
	}
	
	public void deleteProfileImage(User user) throws IOException {
		String imagePath = user.getProfileImg();
		fileService.deleteFile(imagePath);
		user.setProfileImg(null);
	}

	public void addProfileImage(User user, MultipartFile file) throws IOException {
		
		if (!file.isEmpty()) {
			
			if (user.getId() != 0 && user.getProfileImg() != null && !user.getProfileImg().isEmpty()) {
				deleteProfileImage(user);
			}

			String filename = fileService.uploadFile(file);
			
			user.setProfileImg(filename);
		}
	}

}

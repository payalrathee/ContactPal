package com.contactManager.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.services.ContactService;
import com.contactManager.services.UserService;
import com.contactManager.utilities.Message;
import com.contactManager.utilities.Utility;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("currUser")
	public User user(Principal principal) {
		 
		User user = null;
		 if(principal != null) {
			 user = (User) userService.loadUserByUsername(principal.getName());
		 }
		 return user;
	}
	
	@RequestMapping(path="/register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		
		try {
			System.out.println("here");
			if(result.hasErrors()) { 
				return "signup";
			}
			
			user = userService.registerUser(user);
		
			return "redirect:/signinPage";
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			if(e.getMessage().equals("UsernameAlreadyExists")) {
				model.addAttribute("error", new Message("Username alredy exists!", true));
			} else if(e.getMessage().equals("EmailAlreadyExists")) {
				model.addAttribute("error", new Message("Email already exists!", true));
			} else {
				model.addAttribute("error", new Message("Something went wrong! Please try again.", true));
			}
			
			return "signup";
			
		}
	}
	
	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}
	
	@RequestMapping("/updateForm")
	public String updateForm() {
		return "updateProfile";
	}
	
	@RequestMapping(path = "/update",  method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("currUser") User user, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
		
		try {
			
			if(result.hasErrors()) {
				System.out.println(result);
				return "updateProfile";
			}
			
			userService.addProfileImage(user, file);
			
			userService.updateUser(user);
			
			return "redirect:/user/profile";
			
		} catch (Exception e) {
			
			System.out.println(e);
			
			model.addAttribute("error", new Message(e.getMessage(), true));
			
			return "updateProfile";
		}
	}
	
	@RequestMapping("/delete")
	public String delete(Model model, Principal principal ) {
		
		User user = (User) userService.loadUserByUsername(principal.getName());
		try {
			userService.deleteProfileImage(user);
			userService.deleteUser(user);
			
		} catch (Exception e) {
			
			model.addAttribute("error", new Message(e.getMessage(), true));
			
			return "error";
		} 
		return "redirect:/";
	}
	
}

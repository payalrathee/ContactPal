package com.contactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contactManager.models.User;
import com.contactManager.services.UserService;
import com.contactManager.utilities.Message;
import com.contactManager.utilities.Utility;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path="/register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
		
		if(session.getAttribute("currentUser") != null) {
			return "redirect:/contacts";
		}
		
		try {
			
			if(result.hasErrors()) { 
				return "signup";
			}
			
			user = userService.registerUser(user);
		
			return "redirect:/contacts";
			
		} catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("error", new Message(e.getMessage(), true));
			return "error";
			
		}
	}
	
	@RequestMapping(path = "/contacts")
	public String contacts(HttpSession session) {
		
		if(session.getAttribute("currentUser") == null) {
			return "redirect:/signinPage";
		}
		
		// modify model and add contacts
		return "contacts";
	}

}

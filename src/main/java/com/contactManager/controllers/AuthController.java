package com.contactManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contactManager.models.LoginData;
import com.contactManager.models.User;
import com.contactManager.services.AuthService;
import com.contactManager.utilities.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("userCreds") LoginData userCreds, BindingResult result, HttpSession session, Model model) {
		
		if(session.getAttribute("currentUser") != null) {
			return "redirect:/contacts";
		}
		
		try {
			
			if(result.hasErrors()) {
				return "signin";
			}
			
			boolean isValidUser = authService.authenticateUser(userCreds.getUsername(), userCreds.getPassword());
			
			if(!isValidUser) {
				model.addAttribute("error", new Message("Invalid username or password", true));
				return "signin";
			} else {
				return "redirect:/contacts";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("error", new Message(e.getMessage(), true));
			return "error";
		}
	}
}

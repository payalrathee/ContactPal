package com.contactManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contactManager.models.LoginData;
import com.contactManager.models.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(path = "/signupPage", method = RequestMethod.GET)
	public String signup(HttpSession session, User user) {
		
		if(session.getAttribute("currentUser") != null) {
			return "redirect:/contacts";
		} else {
			return "signup";
		}
		
	}
	
	@RequestMapping(path = "/signinPage", method = RequestMethod.GET)
	public String signin(HttpSession session, Model model) {
		model.addAttribute("userCreds", new LoginData());
		
		if(session.getAttribute("currentUser") != null) {
			return "redirect:/contacts";
		} else {
			return "signin";
		}
		
	}
	
	@RequestMapping(path = "/admin", method = RequestMethod.GET)
	public String admin(HttpSession session, Model model) {
		
		User user = (User) session.getAttribute("currentUser");
		if( user == null) {
			
			return "redirect:/signinPage";
		} else {
			if(user.getUserRole().equals("ROLE_ADMIN")) {
				
				// modify model for admin
				return "admin";
			} else {
				
				return "redirect:/contacts";
			}
		}
		
	}
	
	

}

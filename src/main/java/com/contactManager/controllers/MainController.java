package com.contactManager.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.contactManager.models.User;
import com.contactManager.services.UserService;
import com.contactManager.utilities.Message;
import com.contactManager.utilities.Utility;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/", method = {RequestMethod.POST, RequestMethod.GET})
	public String home() {
		
		return "home";
		
	}
	
	@RequestMapping(path = "/dashboard", method = {RequestMethod.POST, RequestMethod.GET})
	public String dashboard() {
		
		return "dashboard";
		
	}
	
	@ModelAttribute("currUser")
	public User user(Principal principal) {
		 
		User user = null;
		if(principal != null) {
			user = (User) userService.loadUserByUsername(principal.getName());
			
		}
		return user;
	}
	
	@RequestMapping(path = "/signupPage", method = RequestMethod.GET)
	public String signup(User user) {
		
		if(Utility.isUserLoggedIn()) {
			return "redirect:/user/contacts";
		}
		
		return "signup";
		
	}
	
	@RequestMapping(path = "/signinPage", method = RequestMethod.GET)
	public String signin(Model model) {
		
		if(Utility.isUserLoggedIn()) {
			return "redirect:/user/contacts";
		}
		
		return "signin";
		
	}
	
	@RequestMapping(path = "/login-error", method = RequestMethod.GET)
    public String loginError(HttpSession session, Model model) {
        
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        
        model.addAttribute("error", new Message(errorMessage, true));
        return "signin";
    }
	
	
}

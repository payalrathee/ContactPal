package com.contactManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String admin(Model model) {
		
		return "admin";
		
	}
	
}

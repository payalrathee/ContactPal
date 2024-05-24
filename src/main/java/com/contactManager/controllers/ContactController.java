package com.contactManager.controllers;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repositories.ContactRepository;
import com.contactManager.services.ContactService;
import com.contactManager.services.UserService;
import com.contactManager.utilities.Message;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	@Autowired
	private UserService userService ;
	
	@ModelAttribute("currUser")
	public User user(Principal principal) {
		 User user = (User) userService.loadUserByUsername(principal.getName());
		 return user;
	}
	
	@RequestMapping(path="/{id}")
	public String getContact(@PathVariable("id") Integer id, Model model, Principal principal) {
		
		try {
			
			User user = (User) userService.loadUserByUsername(principal.getName());
			
			Contact contact = contactService.getContact(id, user);
			model.addAttribute("contact", contact);
			return "view";
			
		} catch(Exception e) {
			
			e.printStackTrace();
			if(e.getMessage().equals("UnauthorizedAccess")) {
				model.addAttribute("error", new Message("You do not have permission to view this contact.", true));
			} else {
				model.addAttribute("error", new Message(e.getMessage(), true));
			}
			
			return "view";
		}
		
	}
	
	@RequestMapping(path="/delete/{id}")
	public String deleteContact(@PathVariable("id") Integer id, Model model, Principal principal) {
		
		try {
			
			User user = (User) userService.loadUserByUsername(principal.getName());
			
			contactService.deleteContact(id, user);
			return "redirect:/contact/all/0";
			
		} catch(Exception e) {
			
			e.printStackTrace();
			if(e.getMessage().equals("UnauthorizedAccess")) {
				model.addAttribute("error", new Message("You do not have permission to delete this contact.", true));
			} else {
				model.addAttribute("error", new Message(e.getMessage(), true));
			}
			
			return "error";
		}
		
	}
	
	@RequestMapping(path = "/all/{page}")
	public String contacts(@PathVariable("page") Integer page, Model model, Principal principal) {
		
		try {
			
			User user = (User) userService.loadUserByUsername(principal.getName());
			Pageable pageable = PageRequest.of(page, 10);
			
			Page<Contact> contacts = contactService.getContacts(user, pageable);
			
			model.addAttribute("contacts", contacts);
			model.addAttribute("curr", page);
			model.addAttribute("totalPages", contacts.getTotalPages());
			return "contacts";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("error", new Message(e.getMessage(), true));
			return "error";
		}
	}

	@RequestMapping(path = "/addForm")
	public String addForm(Contact contact) {
		
		return "addContact";
	}
	
	@RequestMapping(path="/updateForm/{id}")
	public String updateForm(@PathVariable("id") Integer id, Model model, Principal principal) {
		
		try {
			
			User user = (User) userService.loadUserByUsername(principal.getName());
			Contact contact = contactService.getContact(id, user);
			model.addAttribute("contact", contact);
			return "addContact";
			
		} catch(Exception e) {
			
			e.printStackTrace();
			model.addAttribute("error", new Message(e.getMessage(), true));
			return "error";
		}
		
	}
	
	@RequestMapping(path = "/add")
	public String addContact(@Valid @ModelAttribute Contact contact, BindingResult result, @RequestParam("file") MultipartFile file, Model model, Principal principal) {
		
		String username = principal.getName();
		User user = (User) userService.loadUserByUsername(username);
		
		try {
			
			if(result.hasErrors()) {
				return "addContact";
			}
			
			contactService.addContactImage(contact, file);
			
			contactService.addUpdateContact(contact, user);
			return "redirect:/contact/all/0";
			
		} catch (Exception e) {
			
			System.out.println(e);
			
			if(e.getMessage().equals("PhoneAlreadyExists")) {
				model.addAttribute("error", new Message("Contact already exists", true));
			} else {
				model.addAttribute("error", new Message(e.getMessage(), true));
			}
			
			return "addContact";
		}
		
	}
}

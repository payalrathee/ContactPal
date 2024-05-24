package com.contactManager.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repositories.ContactRepository;
import com.contactManager.repositories.UserRepository;

import jakarta.validation.constraints.AssertFalse.List;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private FileHandlerService fileService;

	public Contact getContact(int id, User user) throws Exception {

		Contact contact = contactRepository.findById(id).get();

		if (user.getId() != contact.getUser().getId()) {
			throw new Exception("UnauthorizedAccess");
		}

		return contact;
	}
	
	public Page<Contact> getContacts(User user, Pageable pageable) {

		Page<Contact> contacts = contactRepository.getContactsByUser(user, pageable);
		return contacts;
	}

	public Contact addUpdateContact(Contact contact, User user) throws Exception {

		if (contact.getId() == 0 && contactRepository.getContactByPhoneAndUser(contact.getPhone(), user) != null) {
			throw new Exception("PhoneAlreadyExists");
		}

		// Update user in contact
		contact.setUser(user);

		contactRepository.save(contact);

		return contact;
	}
	
	public void deleteContact(int id, User user) throws Exception {

		Contact contact = contactRepository.findById(id).get();

		if (user.getId() != contact.getUser().getId()) {
			throw new Exception("UnauthorizedAccess");
		}

		deleteContactImage(contact);
		contactRepository.delete(contact);
	}
	
	public void deleteContactImage(Contact contact) throws IOException {
		String imagePath = contact.getImage();
		fileService.deleteFile(imagePath);
		contact.setImage(null);
	}
	
	public void addContactImage(Contact contact, MultipartFile file) throws IOException {

		if (!file.isEmpty()) {

			if (contact.getId() != 0 && contact.getImage() != null && !contact.getImage().isEmpty()) {
				deleteContactImage(contact);
			}

			String filename = fileService.uploadFile(file);
			
			contact.setImage(filename);
		}
	}

}

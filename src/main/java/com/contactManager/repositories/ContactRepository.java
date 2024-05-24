package com.contactManager.repositories;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contactManager.models.Contact;
import com.contactManager.models.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

	public Contact getContactByPhoneAndUser(String phone, User user);
	
	public Page<Contact> getContactsByUser(User user, Pageable pageable);
}

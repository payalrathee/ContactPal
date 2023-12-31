package com.contactManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManager.models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

}

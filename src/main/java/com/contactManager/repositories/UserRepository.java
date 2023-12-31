package com.contactManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManager.models.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}

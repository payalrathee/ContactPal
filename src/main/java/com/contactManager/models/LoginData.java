package com.contactManager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public class LoginData {
	
	@NotBlank(message = "Username can't be empty")
	private String username;
	
	@NotBlank(message = "Password can't be empty")
	private String password;

	public LoginData() {
		super();
	}

	public LoginData(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginData [username=" + username + ", password=" + password + "]";
	}
	
}

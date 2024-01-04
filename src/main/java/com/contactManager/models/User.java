package com.contactManager.models;

import java.util.Set;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "Username can't be empty")
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@NotBlank(message = "Password can't be empty")
	@Size(min = 8, message = "Password must be atleast 8 characters long.")
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="fname")
	private String fname;
	
	@Column(name="lname")
	private String lname;
	
	@Email
	@NotBlank(message = "Email can't be empty")
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Pattern(regexp="(^$|[0-9]{10})", message = "Invalid phone number")
	@Column(name="phone", unique=true, length=12)
	private String phone;
	
	@Column(name="profile_image", columnDefinition="varchar(1000) default 'https://t4.ftcdn.net/jpg/05/89/93/27/360_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N.jpg'")
	private String profileImg;
	
	@Column(name="user_role", nullable=false)
	private String userRole;
	
	@Column(name="about")
	private String about;
	
	@Column(name="status", nullable = false)
	private boolean status;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Contact> contacts;

	public User() {
		super();
	}

	public User(int id, String username, String password, String fname, String lname, String email, String phone,
			String profileImg, String userRole, String about, boolean status, Set<Contact> contacts) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.phone = phone;
		this.profileImg = profileImg;
		this.userRole = userRole;
		this.about = about;
		this.status = status;
		this.contacts = contacts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", fname=" + fname + ", lname="
				+ lname + ", email=" + email + ", phone=" + phone + ", profileImg=" + profileImg + ", userRole="
				+ userRole + ", about=" + about + ", status=" + status + ", contacts=" + contacts + "]";
	}
	
	
	
}

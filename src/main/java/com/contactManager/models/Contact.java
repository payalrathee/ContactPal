package com.contactManager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="contact")
public class Contact {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name = "phone", nullable = false, unique = true)
	private long phone;
	
	@Column(name = "nick_name")
	private String nickName;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "image")
	private String imageUrl;
	
	@Column(name = "note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable( name="user")
	private User user;

	public Contact() {
		super();
	}

	public Contact(int id, String name, long phone, String nickName, String type, String email, String imageUrl,
			String note, User user) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.nickName = nickName;
		this.type = type;
		this.email = email;
		this.imageUrl = imageUrl;
		this.note = note;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", phone=" + phone + ", nickName=" + nickName + ", type=" + type
				+ ", email=" + email + ", imageUrl=" + imageUrl + ", note=" + note + ", user=" + user + "]";
	}

}

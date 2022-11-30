package com.uyghurjava.restapi.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
(name="users")
public class UserDetails {
	@Id @GeneratedValue
	private Long id;
	private String name;
	private String role;
	
	public UserDetails() {
		super();
	}

	public UserDetails(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", name=" + name + ", role=" + role + "]";
	}
	
	
	
}

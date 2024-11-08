package com.forever.app.auth;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Email(message = "please provide a valid email")
	@NotBlank(message = "email should not be null")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "name should not be empty")
	private String name;

	@NotBlank(message = "password cannot be empty")
	@Size(min = 3, message = "password should contain minimum 3 characters")
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles")
	private List<String> roles;
	
	private Long cartId;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long userId,
			@Email(message = "please provide a valid email") @NotBlank(message = "email should not be null") String email,
			@NotBlank(message = "name should not be empty") String name,
			@NotBlank(message = "password cannot be empty") @Size(min = 3, message = "password should contain minimum 3 characters") String password,
			List<String> roles, Long cartId) {
		super();
		this.userId = userId;
		this.email = email;
		this.name = name;
		this.password = password;
		this.roles = roles;
		this.cartId = cartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}


}

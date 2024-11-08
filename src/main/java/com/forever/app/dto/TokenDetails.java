package com.forever.app.dto;

import java.util.List;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class TokenDetails {

	private Long userId;
	private String name;
	private String email;
	private boolean valid;
	private List<String> roles;
	public TokenDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TokenDetails(Long userId, String name, String email, boolean valid, List<String> roles) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.valid = valid;
		this.roles = roles;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}

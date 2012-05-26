package com.memmee.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable{

	private static final long serialVersionUID = 7080674511686049053L;

	private Long id;

    private String email;
    
    private String password;

    private String firstName;

    private String lastName;
    
    private String apiKey;
    
    private Date apiCreationDate;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {   
		this.apiKey = UUID.randomUUID().toString();
	}

	public Date getApiCreationDate() {
		return apiCreationDate;
	}

	public void setApiCreationDate(Date apiCreationDate) {
		this.apiCreationDate = apiCreationDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

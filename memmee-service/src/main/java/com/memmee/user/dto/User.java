package com.memmee.user.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	private static final long serialVersionUID = 7080674511686049053L;

	private Long id;

    @NotNull
    private String email;

    @Size(min = 8, max = 20, message = "Your password must be between 8 and 20 characters")
    private String password;

    private String firstName;

    private String lastName;
    
    private String apiKey;
    
    private Date apiDate;
    
    private Date creationDate;

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
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {   
		this.apiKey = apiKey;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public Date getApiDate() {
		return apiDate;
	}

	public void setApiDate(Date apiDate) {
		this.apiDate = apiDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}

package com.memmee.domain.user.dto;

import com.memmee.domain.password.dto.Password;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 7080674511686049053L;

    private Long id;

    @NotNull
    private String email;

    @NotNull
    private Password password;

    private String firstName;

    private String apiKey;

    private Date apiDate;

    private Date creationDate;

    public User() { super(); }

    public User(String firstName, String email, Password password) {
        super();

        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public void hidePassword() {
        if (this.password != null) {
            this.password.setValue(null);
        }
    }

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

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
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

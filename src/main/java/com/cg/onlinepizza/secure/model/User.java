package com.cg.onlinepizza.secure.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "userlogin")
public class User {
    @GeneratedValue
    private int id;
    @Id
    @Column
    @NotBlank
    private String username;
    @Column
    @JsonIgnore
    @NotBlank
    private String password;
    private String role;

    public User() {
    	
    }
    public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
        return username;
    }
	
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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

}


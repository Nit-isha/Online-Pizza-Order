package com.cg.onlinepizza.secure.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "userlogin")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
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


package com.cg.onlinepizza.secure.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.checkerframework.common.aliasing.qual.Unique;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "userlogin")
public class User {
    @Id
    @GeneratedValue/*(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @GenericGenerator(name = "user_seq",
    strategy = "com.cg.onlinepizza.sequencegen.StringPrefixedSequenceIdGenerator"'
    parameters = {
    	@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
    	@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "2022"),
    	@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")})*/
    private int id;
    @Column(unique = true)
    @NotBlank
    @Unique
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
    public User(String username, String password, String role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
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


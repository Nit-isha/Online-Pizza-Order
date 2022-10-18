package com.cg.onlinepizza.secure.model;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "admin");
    }
    
}

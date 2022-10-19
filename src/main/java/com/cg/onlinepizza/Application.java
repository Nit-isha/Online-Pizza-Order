package com.cg.onlinepizza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cg.onlinepizza.secure.model.User;
import com.cg.onlinepizza.secure.service.JwtUserDetailsService;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    @Value("${admin.username}")
    private String adminusr;
    @Value("${admin.password}")
    private String adminpwd;
    @Value("${admin.role}")
    private String adminrole;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String autoCreate;
    
    @Override
    public void run(String... args) {
        if (autoCreate.equals("create")) {
            User user = new User(adminusr, adminpwd, adminrole);
            userDetailsService.save(user);
        }
    }
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

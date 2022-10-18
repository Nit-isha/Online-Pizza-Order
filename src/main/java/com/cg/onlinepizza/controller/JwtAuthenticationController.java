package com.cg.onlinepizza.controller;


import com.cg.onlinepizza.config.JwtTokenUtil;
import com.cg.onlinepizza.customer.dto.*;
import com.cg.onlinepizza.secure.model.JwtRequest;
import com.cg.onlinepizza.secure.model.JwtResponse;
import com.cg.onlinepizza.secure.model.User;
import com.cg.onlinepizza.secure.model.UserDto;
import com.cg.onlinepizza.secure.service.JwtUserDetailsService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@Valid @RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody CustomerDto customerDto) throws Exception {
	    
		return ResponseEntity.ok(userDetailsService.save(customerDto));
	}

	@Valid @RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> saveAdmin() throws Exception {
	    User user = new User("admin", "admin", "admin");
	    return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	/*-----------------------------------------------------------------------------------------//
	@Value("${admin.username}")
	private String adminusr;
	@Value("${admin.password}")
	private String adminpwd;
	@Value("${admin.role}")
	private String adminrole;
	User user = new User(adminusr,adminpwd, adminrole );
	userDeta*/
}

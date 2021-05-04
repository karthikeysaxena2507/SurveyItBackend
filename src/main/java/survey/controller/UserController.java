package survey.controller;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import survey.repository.*;
import survey.security.jwt.JwtUtils;
import survey.security.services.UserDetailsImpl;
import survey.model.*;
import survey.payload.JwtResponse;
import survey.payload.LoginRequest;
import survey.payload.MessageResponse;
import survey.payload.SignupRequest;

@CrossOrigin(origins = "https://survey-it.netlify.app/")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		System.out.println("Username: " + loginRequest.getUsername());
		System.out.println("Password: " + loginRequest.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		System.out.println("Authentication " + authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail()));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		User user = new User(
				signUpRequest.getUsername(), 
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		
		System.out.println(user.getEmail() + " " + user.getPassword() + " " + user.getUsername());
		
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{token}")
	public String getUserfromToken(@PathVariable String token) {
		System.out.println("Token: " + token);
		String username = jwtUtils.getUserNameFromJwtToken(token);
		System.out.println("User From Token" + username);
		return username;
	}
	
}

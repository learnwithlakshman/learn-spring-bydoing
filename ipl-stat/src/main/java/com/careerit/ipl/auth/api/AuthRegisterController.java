package com.careerit.ipl.auth.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.careerit.ipl.auth.JwtTokenUtil;
import com.careerit.ipl.auth.model.LoginReponse;
import com.careerit.ipl.auth.model.LoginUser;
import com.careerit.ipl.auth.model.User;
import com.careerit.ipl.auth.service.UserService;

@RestController
public class AuthRegisterController {

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@SuppressWarnings("rawtypes")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody User user) {

		User userExists = userDetailsService.findByUsername(user.getUsername());
		if (userExists != null) {
			return ResponseEntity.badRequest().body("User with given email/username already exists");
		}
		User regUser = userDetailsService.saveUser(user);
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "User registered successfully with id:" + regUser.getId());
		return ResponseEntity.ok(model);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginUser loginUser) throws Exception {
		authenticate(loginUser.getUsername(), loginUser.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new LoginReponse(token));
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

}

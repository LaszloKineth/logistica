package kinela.logistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kinela.logistic.dto.LoginUserDTO;
import kinela.logistic.security.JwtService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginUserDTO loginUserDTO) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUserDTO.getUserName(), loginUserDTO.getUserPassword()));
		
		return ResponseEntity.ok(jwtService.creatJwtToken((UserDetails)authentication.getPrincipal()));
	}
}
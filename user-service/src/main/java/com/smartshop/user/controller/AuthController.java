package com.smartshop.user.controller;

import com.smartshop.user.dto.LoginRequest;
import com.smartshop.user.dto.RegisterRequest;
import com.smartshop.user.service.JwtService;
import com.smartshop.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

 @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){
      userService.registerUser(registerRequest);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body("User Registered successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
      UsernamePasswordAuthenticationToken unauthToken =
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
      Authentication auth= authenticationManager.authenticate(unauthToken);
      if(auth.isAuthenticated()){
        return ResponseEntity.ok().body(jwtService.generateToken((UserDetails) auth.getPrincipal()));
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong username or password");

  }
}

package com.smartshop.user.controller;

import com.smartshop.user.dto.RegisterRequest;
import com.smartshop.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

 @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){
      if(userService.fetchUser(registerRequest.getEmail())!=null){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Email already exists");
      }
      try {
        userService.save(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("User registered successfully");
      } catch (DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Email uniqueness constraint voilated");
      } catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Registration failed");
      }
  }

}

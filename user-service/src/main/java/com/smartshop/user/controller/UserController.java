package com.smartshop.user.controller;

import com.smartshop.user.dto.UpdateProfileRequest;
import com.smartshop.user.dto.UserProfile;
import com.smartshop.user.model.User;
import com.smartshop.user.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @GetMapping("/get-user")
    List<User> getUsers(){
     return userService.getUser();
    }

  @PutMapping("/update-user/{uuid}")
  User updateUser(@PathVariable UUID uuid, @RequestBody UpdateProfileRequest updateProfileRequest){
    return userService.updateProfile(uuid,updateProfileRequest);
  }

//  @GetMapping("/me")
//  public ResponseEntity<UserProfile> getCurrentUserDetails(@RequestHeader ("X-User-Id") String email){
//
//      return ResponseEntity.ok(userService.fetchUserProfile(email));
//  }

  @GetMapping("/me")
  public ResponseEntity<UserProfile> getCurrentUserDetails(@AuthenticationPrincipal User user){
    return ResponseEntity.ok(userService.fetchUserProfile(user.getEmail()));
  }

}

package com.smartshop.user.controller;

import com.smartshop.user.dto.UpdateProfileRequest;
import com.smartshop.user.model.User;
import com.smartshop.user.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
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

  @GetMapping("/user/me")
  User getCurrentUserDetails(Authentication authentication){

 return null;
  }

}

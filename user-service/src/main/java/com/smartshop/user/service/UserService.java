package com.smartshop.user.service;

import com.smartshop.user.dto.RegisterRequest;
import com.smartshop.user.dto.UpdateProfileRequest;
import com.smartshop.user.exception.UserAlreadyExistsException;
import com.smartshop.user.model.User;
import com.smartshop.user.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

   private final PasswordEncoder passwordEncoder;
   private final UserRepository userRepository;

  public void save(RegisterRequest registerRequest){
    User user = convertToUser(registerRequest);
    userRepository.save(user);
  }
  public void registerUser(RegisterRequest registerRequest){
    if(fetchUser(registerRequest.getEmail())!=null){
      throw new UserAlreadyExistsException("Email already exists");
    }
      save(registerRequest);
  }

  public User convertToUser(RegisterRequest registerRequest){
    User user=new User();
    user.setName(registerRequest.getName());
    user.setEmail(registerRequest.getEmail());
    user.setRole(registerRequest.getRole());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setMobileNo(registerRequest.getMobileNo());
    user.setCreatedAt(Instant.now());
    return user;
  }

  public User fetchUser(String email){
    return userRepository.findByEmail(email).orElse(null);
  }

  public User updateProfile(UUID uuid, UpdateProfileRequest request){
    User user= userRepository.findById(uuid).orElseThrow(
        ()-> new RuntimeException("User not found with uuid "+uuid));
    if(request.getName() != null && !request.getName().isBlank()){
      user.setName(request.getName());
    }
    if(request.getMobileNo()!= null && !request.getMobileNo().isBlank()){
      user.setMobileNo(request.getMobileNo());
    }
    return userRepository.save(user);
  }

  public List<User> getUser(){
    return userRepository.findAll();
  }
}

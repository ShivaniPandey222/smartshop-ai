package com.smartshop.user.service;

import com.smartshop.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  UserDetailsServiceImpl(UserRepository userRepository){
    this.userRepository=userRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email is not present"));
  }

}

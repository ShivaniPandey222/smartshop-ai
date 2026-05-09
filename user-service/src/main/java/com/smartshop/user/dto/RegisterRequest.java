package com.smartshop.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

  @NotBlank
  private String name;
  @NotBlank
  private String password;

  private String mobileNo;
  @NotBlank
  private String email;

  private String role;
}

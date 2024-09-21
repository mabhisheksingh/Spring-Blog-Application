package com.blog.service;

import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public interface UserService {
     public UserDTO getUserByUserName(String userName) ;
     public @NotNull @Valid RegisterUserDTO saveUser(RegisterUserDTO userDTo);

}

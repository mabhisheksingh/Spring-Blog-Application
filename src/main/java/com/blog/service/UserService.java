package com.blog.service;

import com.blog.dto.PagedDTO;
import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;

public interface UserService {

  public UserDTO getUserByUserName(String userName);

  public RegisterUserDTO saveUser(RegisterUserDTO userDTo);

  public PagedDTO<UserDTO> getUserList(Integer pageNo, Integer pageSize);

  public UserDTO getUserById(String id);
  //     public UserDTO updateUser(UserDTO userDTO);
  public void deleteUser(String userName);
}

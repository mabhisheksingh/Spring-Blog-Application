package com.blog.service.impl;

import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;
import com.blog.exception.BlogException;
import com.blog.model.User;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;

import java.net.http.HttpClient;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RegisterUserDTO saveUser(RegisterUserDTO userDTo) {

        if (getUserByUserName(userDTo.getUserName()) != null) {
            throw new BlogException("UserName already exist :: " + userDTo.getUserName(),HttpStatus.CONFLICT.value());
        }

        User user = User.builder()
                .name(userDTo.getName())
                .userName(userDTo.getUserName())
                .email(userDTo.getEmail())
                .gender(userDTo.getGender())
                .mobileNumber(userDTo.getMobileNumber())
                .build();
        user = userRepository.save(user);
        if (Objects.nonNull(user)) {
            userDTo.setId(user.getId());
        }
        return userDTo;
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        System.out.println(user);

        if (Objects.isNull(user)) {
            throw new BlogException("Username Not found in Db:: " + userName, HttpStatus.NOT_FOUND.value());
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setGender(user.getGender());
        return userDTO;
    }
}

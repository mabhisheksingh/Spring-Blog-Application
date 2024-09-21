package com.blog.controller;

import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;
import com.blog.service.UserService;
import io.micrometer.core.ipc.http.HttpSender;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.blog.utils.constants.APIPathConstant.V1_USER_BASE_PATH;

@Tag(name = "User APIs" ,description = "User APIs for blog application.")
@RestController
@RequestMapping(V1_USER_BASE_PATH)
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    private final Logger logger = Logger.getLogger(UserController.class);

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@NotNull @Valid @RequestBody RegisterUserDTO registerUserDTO){
        logger.info(registerUserDTO);
        registerUserDTO = userService.saveUser(registerUserDTO);
        return ResponseEntity.ok(registerUserDTO);
    }

    @GetMapping("/get-user/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName){

        UserDTO userDTO = new UserDTO();
        userDTO.setId("12L");
        userDTO.setUserName(userName);
        userDTO.setName("John");
        userDTO.setEmail("john.doe@example.com");
        userDTO = userService.getUserByUserName(userName);
        return ResponseEntity.ok(userDTO);
    }
}

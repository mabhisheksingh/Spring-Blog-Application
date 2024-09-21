package com.blog.controller;

import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;
import io.micrometer.core.ipc.http.HttpSender;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.blog.utils.constants.APIPathConstant.V1_USER_BASE_PATH;

@Tag(name = "User APIs" ,description = "User APIs for blog application.")
@RestController
@RequestMapping(V1_USER_BASE_PATH)
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class);

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@NotNull @Valid @RequestBody RegisterUserDTO registerUserDTO){
        logger.info(registerUserDTO);
        registerUserDTO.setId(123L);
        return ResponseEntity.ok(registerUserDTO);
    }
}

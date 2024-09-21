package com.blog.controller;


import com.blog.model.LoginRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.blog.utils.constants.APIPathConstant.V1_AUTH_BASE_PATH;

@Tag(name = "Auth APIs" ,description = "Auth APIs for blog application.")
@RestController
@RequestMapping(V1_AUTH_BASE_PATH)
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @NotNull LoginRequest loginRequest){
        return ResponseEntity.ok().build();
        }
}

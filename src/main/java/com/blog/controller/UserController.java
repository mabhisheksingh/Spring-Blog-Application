package com.blog.controller;

import com.blog.dto.PagedDTO;
import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;
import com.blog.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.blog.utils.constants.APIPathConstant.V1_USER_BASE_PATH;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User APIs", description = "User APIs for blog application.")
@RestController
@RequestMapping(V1_USER_BASE_PATH)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = Logger.getLogger(UserController.class);

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@NotNull @Valid @RequestBody RegisterUserDTO registerUserDTO) {
        logger.info("Inside registerUser");
        registerUserDTO = userService.saveUser(registerUserDTO);
        return ResponseEntity.ok(registerUserDTO);
    }

    @GetMapping("/get-user/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        logger.info("Inside getUser");
        UserDTO userDTO = new UserDTO();
        userDTO.setId("12L");
        userDTO.setUserName(userName);
        userDTO.setName("John");
        userDTO.setEmail("john.doe@example.com");
        userDTO = userService.getUserByUserName(userName);
        return ResponseEntity.ok(userDTO);
    }

    // For admin to get all user from Mongo
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getUserList(
            @NotNull @RequestParam(defaultValue = "0", name = "pageNo", required = true) Integer pageNo,
            @NotNull @RequestParam(name = "pageSize", defaultValue = "1", required = true) @Max(50) @Min(1) Integer offset) {
        logger.info("Inside getUserList");
        PagedDTO<UserDTO> userDTOList = userService.getUserList(pageNo, offset);
        return ResponseEntity.ok(userDTOList);
    }

    @DeleteMapping("/delete-user/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        logger.info("Inside deleteUser");
        userService.deleteUser(userName);
        return ResponseEntity.ok().build();
    }

}

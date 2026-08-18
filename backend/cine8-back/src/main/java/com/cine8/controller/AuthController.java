package com.cine8.controller;

import com.cine8.dto.RegisterDTO;
import com.cine8.entity.User;
import com.cine8.repository.UserRepository;
import com.cine8.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {

        User user = new User();

        user.setName(registerDTO.getName());
        user.setSurnames(registerDTO.getSurnames());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());


        return ResponseEntity.ok().body(userService.saveUser(user));
    }


}

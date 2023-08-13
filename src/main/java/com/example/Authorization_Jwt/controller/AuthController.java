package com.example.Authorization_Jwt.controller;

import com.example.Authorization_Jwt.Dto.JwtRequest;
import com.example.Authorization_Jwt.Dto.JwtResponse;
import com.example.Authorization_Jwt.Dto.UserDTO;
import com.example.Authorization_Jwt.helper.JwtTokenUtil;
import com.example.Authorization_Jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;



    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.signUp(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody JwtRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        String role = jwtRequest.getRole(); // Get the role from the request
        String token = userService.signIn(username, password, role);
        UserDTO userDetails = userService.findByUsername(username);
        return ResponseEntity.ok(new JwtResponse(token, userDetails));
    }

}

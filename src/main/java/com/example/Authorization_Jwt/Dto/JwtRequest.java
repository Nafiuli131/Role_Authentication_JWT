package com.example.Authorization_Jwt.Dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
    private String role;
}

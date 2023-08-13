package com.example.Authorization_Jwt.Dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;
}

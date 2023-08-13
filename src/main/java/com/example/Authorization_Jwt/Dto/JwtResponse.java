package com.example.Authorization_Jwt.Dto;

public class JwtResponse {

    private final String token;
    private final UserDTO user;

    public JwtResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }
}

package com.example.Authorization_Jwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping()
    public String getMessage() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"));

        if (hasRole) {
            return "hello admin";
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @GetMapping("/user")
    public String getUserMessage() throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasRole = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_user"));

        if (hasRole) {
            return "hello user";
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @GetMapping("/both")
    public String getUser_Admin() throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUser = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_user"));
        boolean hasAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"));
        if (hasAdmin || hasUser) {
            return "hello all";
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }
}

package com.example.Authorization_Jwt.service;

import com.example.Authorization_Jwt.Dto.UserDTO;
import com.example.Authorization_Jwt.entity.User;
import com.example.Authorization_Jwt.repository.UserRepository;
import com.example.Authorization_Jwt.helper.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public UserDTO signUp(UserDTO userDTO) {
        // Validate and map userDTO to User entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole()); // Set user role
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(user); // Save the user entity in the database

        return userDTO;
    }

    public String signIn(String username, String password, String role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
        if(!(user.getPassword().equals(password))){
            throw new RuntimeException("Invalid password");
        }
        // Generate token with the username and role
        return jwtTokenUtil.generateToken(username, role,user.getEmail(),user.getPhoneNumber());
    }


    public UserDTO findByUsername(String username) {
        // You should fetch additional user details and construct a UserDTO
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}

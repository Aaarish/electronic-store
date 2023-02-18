package com.example.electronicstore.service.impl;

import com.example.electronicstore.jwt.JwtRequest;
import com.example.electronicstore.jwt.JwtResponse;
import com.example.electronicstore.jwt.JwtUtils;
import com.example.electronicstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword());
        Authentication authenticatedRequest = authenticationManager.authenticate(authenticationToken);

        String token = null;

        if(authenticatedRequest != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
            token = jwtUtils.generateToken(userDetails);
        }
        else{
            throw new RuntimeException("User cannot be authenticated !!");
        }

        return JwtResponse.builder()
                .jwt(token)
                .build();
    }
}

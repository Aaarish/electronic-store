package com.example.electronicstore.service;

import com.example.electronicstore.jwt.JwtRequest;
import com.example.electronicstore.jwt.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest jwtRequest);
}

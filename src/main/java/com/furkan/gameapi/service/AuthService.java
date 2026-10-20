package com.furkan.gameapi.service;

import com.furkan.gameapi.dto.AuthResponse;
import com.furkan.gameapi.dto.LoginRequest;
import com.furkan.gameapi.exception.AppUserNotFoundException;
import com.furkan.gameapi.model.AppUser;
import com.furkan.gameapi.repository.AppUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        AppUser appUser = appUserRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppUserNotFoundException(request.getUsername()));
        String token = jwtService.generateToken(appUser);
        return new AuthResponse(token);
    }
}

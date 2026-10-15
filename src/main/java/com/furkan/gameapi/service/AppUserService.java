package com.furkan.gameapi.service;

import com.furkan.gameapi.dto.AppUserCreateRequest;
import com.furkan.gameapi.exception.AppUserNotFoundException;
import com.furkan.gameapi.model.AppUser;
import com.furkan.gameapi.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser getUserByUsername(String username){
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new AppUserNotFoundException(username));
        return user;
    }

    public AppUser register(AppUserCreateRequest request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        AppUser user = new AppUser(request.getUsername(), encodedPassword, "USER");

        return appUserRepository.save(user);
    }
}

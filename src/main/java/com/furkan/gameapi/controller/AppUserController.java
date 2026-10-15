package com.furkan.gameapi.controller;

import com.furkan.gameapi.dto.AppUserCreateRequest;
import com.furkan.gameapi.dto.AppUserResponse;
import com.furkan.gameapi.mapper.AppUserMapper;
import com.furkan.gameapi.model.AppUser;
import com.furkan.gameapi.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AppUserController {
    private final AppUserService appUserService;
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<AppUserResponse> getUserByUsername(@PathVariable String username) {
        AppUser user = appUserService.getUserByUsername(username);
        AppUserResponse response = AppUserMapper.toAppUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserResponse> registerUser(@Valid @RequestBody AppUserCreateRequest appUserCreateRequest) {
        AppUser user = appUserService.register(appUserCreateRequest);
        AppUserResponse response = AppUserMapper.toAppUserResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

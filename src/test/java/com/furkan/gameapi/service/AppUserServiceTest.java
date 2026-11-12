package com.furkan.gameapi.service;

import com.furkan.gameapi.dto.AppUserCreateRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.furkan.gameapi.exception.UserAlreadyExistsException;
import com.furkan.gameapi.model.AppUser;
import com.furkan.gameapi.model.Role;
import com.furkan.gameapi.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {
    @Mock
    AppUserRepository appUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AppUserService appUserService;

    @Test
    void register_shouldThrowException_whenUsernameAlreadyExists() {
        AppUserCreateRequest appUserCreateRequest = new AppUserCreateRequest("abc","1231");

        when(appUserRepository.existsByUsername("abc")).thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> appUserService.register(appUserCreateRequest)
        );

        verify(appUserRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void register_shouldSaveUser_whenUsernameDoesNotExist() {
        AppUserCreateRequest request = new AppUserCreateRequest("abc", "1231");

        when(appUserRepository.existsByUsername("abc")).thenReturn(false);
        when(passwordEncoder.encode("1231")).thenReturn("encoded123");

        appUserService.register(request);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);

        verify(appUserRepository).save(captor.capture());

        AppUser savedUser = captor.getValue();

        assertEquals("abc", savedUser.getUsername());
        assertEquals("encoded123", savedUser.getPassword());
        assertEquals(Role.USER, savedUser.getRole());

        verify(passwordEncoder).encode("1231");
        verify(appUserRepository).existsByUsername("abc");
    }
}

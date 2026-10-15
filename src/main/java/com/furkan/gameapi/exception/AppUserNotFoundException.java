package com.furkan.gameapi.exception;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException(String username) {
        super("User with username '" + username + "' not found");
    }
}

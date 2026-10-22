package com.furkan.gameapi.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
            super("User with name " + username + " already exists");
    }
}

package com.furkan.gameapi.dto;

public class AppUserResponse {
    private Integer id;
    private String username;
    private String role;

    public AppUserResponse(Integer id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public AppUserResponse() {}

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

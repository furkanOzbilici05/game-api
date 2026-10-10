package com.furkan.gameapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PlayerCreateRequest {

    @NotBlank(message = "Player name cannot be blank")
    private String name;

    @Min(value = 0, message = "Health cannot be negative")
    private int health;

    public PlayerCreateRequest() {}

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

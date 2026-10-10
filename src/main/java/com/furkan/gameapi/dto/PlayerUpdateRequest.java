package com.furkan.gameapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PlayerUpdateRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Min(value = 0,message = "Health cannot be negative")
    private int health;

    public PlayerUpdateRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

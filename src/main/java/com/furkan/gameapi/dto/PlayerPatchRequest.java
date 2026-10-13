package com.furkan.gameapi.dto;

import jakarta.validation.constraints.Min;

public class PlayerPatchRequest {
    private String name;
    @Min(value = 0, message = "Health cannot be negative")
    private Integer health;

    public PlayerPatchRequest() {}

    public PlayerPatchRequest(String name, Integer health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
}

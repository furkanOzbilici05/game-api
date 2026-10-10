package com.furkan.gameapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class WeaponCreateRequest {
    @NotBlank
    private String name;

    @Min(0)
    private int damage;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public WeaponCreateRequest(){}
}

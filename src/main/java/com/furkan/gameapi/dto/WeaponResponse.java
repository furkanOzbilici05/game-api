package com.furkan.gameapi.dto;

public class WeaponResponse {
    private Integer id;
    private String name;
    private int damage;

    public WeaponResponse(Integer id, String name, int damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }
}

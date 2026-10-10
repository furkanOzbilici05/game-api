package com.furkan.gameapi.dto;

public class PlayerResponse {
    private Integer id;
    private String name;
    private int health;
    private WeaponResponse weapon;

    public PlayerResponse(Integer id, String name, int health, WeaponResponse weapon) {
        this.id  = id;
        this.name = name;
        this.health = health;
        this.weapon = weapon;
    }

    public WeaponResponse getWeapon() {
        return weapon;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}

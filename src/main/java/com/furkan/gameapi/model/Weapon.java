package com.furkan.gameapi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weapons")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private int damage;

    @OneToMany(mappedBy = "weapon")
    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public Weapon(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}

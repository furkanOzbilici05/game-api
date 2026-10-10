package com.furkan.gameapi.model;
import jakarta.persistence.*;

@Entity
@Table(name = "players")

public class Player {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_id")
    private Weapon weapon;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int health;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    public Player() {}

    public Weapon getWeapon(){
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}

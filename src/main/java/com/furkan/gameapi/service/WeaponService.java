package com.furkan.gameapi.service;

import com.furkan.gameapi.model.Weapon;
import com.furkan.gameapi.repository.WeaponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponService {
    private final WeaponRepository weaponRepository;

    public WeaponService(WeaponRepository weaponRepository){
        this.weaponRepository = weaponRepository;
    }

    public Weapon createWeapon(Weapon weapon){
        return weaponRepository.save(weapon);
    }

    public Weapon getWeaponById(int id){
        return weaponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Weapon with id " + id + " not found"));
    }

    public List<Weapon> getAllWeapons(){
        return weaponRepository.findAll();
    }
}

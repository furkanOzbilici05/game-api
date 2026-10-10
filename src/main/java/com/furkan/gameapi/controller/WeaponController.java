package com.furkan.gameapi.controller;

import com.furkan.gameapi.dto.WeaponCreateRequest;
import com.furkan.gameapi.mapper.PlayerMapper;
import com.furkan.gameapi.model.Weapon;
import com.furkan.gameapi.service.WeaponService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeaponController {
    private final WeaponService weaponService;

    public WeaponController(WeaponService weaponService){
        this.weaponService = weaponService;
    }

    @PostMapping("/weapons")
    public Weapon createWeapon(@RequestBody @Valid WeaponCreateRequest request){
        Weapon weapon = PlayerMapper.toWeaponEntity(request);
        return weapon;
    }
}

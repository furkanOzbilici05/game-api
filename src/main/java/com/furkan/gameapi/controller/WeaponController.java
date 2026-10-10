package com.furkan.gameapi.controller;

import com.furkan.gameapi.dto.WeaponCreateRequest;
import com.furkan.gameapi.dto.WeaponResponse;
import com.furkan.gameapi.mapper.PlayerMapper;
import com.furkan.gameapi.model.Weapon;
import com.furkan.gameapi.service.WeaponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WeaponController {
    private final WeaponService weaponService;

    public WeaponController(WeaponService weaponService){
        this.weaponService = weaponService;
    }

    @GetMapping("/weapons/{id}")
    public ResponseEntity<WeaponResponse> getWeaponById(@PathVariable Integer id){
        Weapon weapon = weaponService.getWeaponById(id);
        WeaponResponse response = PlayerMapper.toWeaponResponse(weapon);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/weapons")
    public ResponseEntity<List<WeaponResponse>> getWeapons(){
        List<Weapon> weapons = weaponService.getAllWeapons();
        List<WeaponResponse> responses = weapons.stream().map(PlayerMapper::toWeaponResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/weapons")
    public ResponseEntity<WeaponResponse> createWeapon(@RequestBody @Valid WeaponCreateRequest request){
        Weapon weapon = PlayerMapper.toWeaponEntity(request);
        Weapon createdWeapon = weaponService.createWeapon(weapon);

        WeaponResponse response = PlayerMapper.toWeaponResponse(createdWeapon);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}


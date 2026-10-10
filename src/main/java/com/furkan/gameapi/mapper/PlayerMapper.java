package com.furkan.gameapi.mapper;

import com.furkan.gameapi.dto.*;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.model.Weapon;

public class PlayerMapper {
    public static PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getName(),
                player.getHealth(),
                toWeaponResponse(player.getWeapon())
        );
    }

    public static Player toEntity(PlayerCreateRequest request) {
        return new Player(
          request.getName(),
          request.getHealth()
        );
    }

    public static Player toUpdate(PlayerUpdateRequest request) {
        return new Player(
                request.getName(),
                request.getHealth()
        );
    }

    public static WeaponResponse toWeaponResponse(Weapon weapon){
        if (weapon == null) {
            return null;
        }

        return new WeaponResponse(
                weapon.getId(),
                weapon.getName(),
                weapon.getDamage()
        );
    }

    public static Weapon toWeaponEntity(WeaponCreateRequest request){
        return new Weapon(request.getName(),request.getDamage());
    }
}

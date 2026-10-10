package com.furkan.gameapi.repository;

import com.furkan.gameapi.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, Integer> {

}

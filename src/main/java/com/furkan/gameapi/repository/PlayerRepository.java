package com.furkan.gameapi.repository;

import com.furkan.gameapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByHealthGreaterThan(int health);
    Optional<Player> findByName(String name);
    List<Player> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Player p LEFT JOIN FETCH p.weapon")
    List<Player> findAllWithWeapon();
}

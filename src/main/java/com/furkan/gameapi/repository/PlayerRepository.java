package com.furkan.gameapi.repository;

import com.furkan.gameapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByHealthGreaterThan(int health);
    Optional<Player> findByName(String name);
    List<Player> findByNameContainingIgnoreCase(String name);

    @Query(
            value = "SELECT p FROM Player p LEFT JOIN FETCH p.weapon",
            countQuery = "SELECT COUNT(p) FROM Player p"
    )
    Page<Player> findAllWithWeapon(Pageable pageable);
}

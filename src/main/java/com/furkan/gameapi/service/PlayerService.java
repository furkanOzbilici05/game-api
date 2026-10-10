package com.furkan.gameapi.service;

import com.furkan.gameapi.exception.PlayerNotFoundException;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.model.Weapon;
import com.furkan.gameapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final WeaponService weaponService;

    public PlayerService(PlayerRepository playerRepository,WeaponService weaponService) {
        this.playerRepository = playerRepository;
        this.weaponService = weaponService;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAllWithWeapon();
    }

    public Player createPlayer(Player player){
        if (player.getName() == null || player.getName().isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank");
        }

        if (player.getHealth() < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }

        return playerRepository.save(player);
    }

    public Player getPlayerById(int id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found"));
    }

    public void deletePlayer(int id) {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }

    public Player updatePlayer(int id, Player updatedPlayer) {
        Player player = getPlayerById(id);

        player.setName(updatedPlayer.getName());
        player.setHealth(updatedPlayer.getHealth());
        return playerRepository.save(player);
    }

    public List<Player> getPlayersByMinHealth(int minHealth) {
        return playerRepository.findByHealthGreaterThan(minHealth);
    }

    public Player getPlayerByName(String name){
        return playerRepository.findByName(name).orElseThrow(() -> new PlayerNotFoundException("Player with name " + name + " not found"));
    }

    public List<Player> searchPlayersByName(String name){
        return playerRepository.findByNameContainingIgnoreCase(name);
    }

    public Player equipWeapon(int playerId, int weaponId){
        Player player = getPlayerById(playerId);
        Weapon weapon = weaponService.getWeaponById(weaponId);

        player.setWeapon(weapon);

        return playerRepository.save(player);
    }

    public Player unequipWeapon(int playerId){
        Player player = getPlayerById(playerId);
        player.setWeapon(null);
        return playerRepository.save(player);
    }
}

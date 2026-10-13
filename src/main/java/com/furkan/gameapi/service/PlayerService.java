package com.furkan.gameapi.service;

import com.furkan.gameapi.dto.PlayerPatchRequest;
import com.furkan.gameapi.exception.PlayerNotFoundException;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.model.Weapon;
import com.furkan.gameapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Player updatePlayer(int id, Player updatedPlayer) {
        Player player = getPlayerById(id);

        player.setName(updatedPlayer.getName());
        player.setHealth(updatedPlayer.getHealth());
        return player;
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

    @Transactional
    public Player equipWeapon(int playerId, int weaponId){
        Player player = getPlayerById(playerId);
        Weapon weapon = weaponService.getWeaponById(weaponId);

        player.setWeapon(weapon);

        return player;
    }

    @Transactional
    public Player unequipWeapon(int playerId){
        Player player = getPlayerById(playerId);
        player.setWeapon(null);
        return player;
    }
    
    @Transactional
    public Player patchPlayer(int id, PlayerPatchRequest request){
        Player player = getPlayerById(id);

        if (request.getName() != null) {
            if (request.getName().isBlank()) {
                throw new IllegalArgumentException("Player name cannot be blank");
            }

            player.setName(request.getName());
        }

        if (request.getHealth() != null) {
            player.setHealth(request.getHealth());
        }

        return player;
    }

    @Transactional
    public Player healPlayer(int playerId, int amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        Player player = getPlayerById(playerId);
        player.setHealth(player.getHealth() + amount);
        return player;
    }
}

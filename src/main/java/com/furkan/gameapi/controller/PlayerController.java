package com.furkan.gameapi.controller;

import com.furkan.gameapi.dto.PlayerCreateRequest;
import com.furkan.gameapi.dto.PlayerResponse;
import com.furkan.gameapi.dto.PlayerUpdateRequest;
import com.furkan.gameapi.mapper.PlayerMapper;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerResponse>> getPlayers(){
        List<Player> players = playerService.getAllPlayers();
        List<PlayerResponse> responses = players.stream().map(PlayerMapper::toResponse).toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/players/search")
    public Player searchPlayer(@RequestParam String name){
        return playerService.getPlayerByName(name);
    }

    @GetMapping("/players/search-partial")
    public List<Player> searchPlayersByName(@RequestParam String name) {
        return playerService.searchPlayersByName(name);
    }
    @GetMapping("/players/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable int id) {
        Player player = playerService.getPlayerById(id);

        PlayerResponse response = PlayerMapper.toResponse(player);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/players/filter")
    public List<Player> filterPlayer(@RequestParam int minHealth) {
        return playerService.getPlayersByMinHealth(minHealth);
    }

    @PostMapping("/players")
    public ResponseEntity<PlayerResponse> createPlayer(@RequestBody @Valid PlayerCreateRequest request){

        Player player = PlayerMapper.toEntity(request);

        Player createdPlayer = playerService.createPlayer(player);

        PlayerResponse response = PlayerMapper.toResponse(createdPlayer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable int id, @RequestBody @Valid PlayerUpdateRequest request){
        Player player = PlayerMapper.toUpdate(request);
        Player updatedPlayer = playerService.updatePlayer(id,player);
        PlayerResponse response = PlayerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("players/{playerId}/weapon/{weaponId}")
    public ResponseEntity<PlayerResponse> equipWeapon(@PathVariable int playerId, @PathVariable int weaponId){
        Player updatedPlayer = playerService.equipWeapon(playerId, weaponId);
        PlayerResponse response = PlayerMapper.toResponse(updatedPlayer);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable int id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/players/{playerId}/weapon")
    public ResponseEntity<PlayerResponse> unequipWeapon(@PathVariable int playerId){
        Player player = playerService.unequipWeapon(playerId);
        PlayerResponse response = PlayerMapper.toResponse(player);
        return ResponseEntity.ok(response);
    }

}

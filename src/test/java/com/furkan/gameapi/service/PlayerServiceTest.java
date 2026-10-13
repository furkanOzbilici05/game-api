package com.furkan.gameapi.service;

import com.furkan.gameapi.dto.PlayerPatchRequest;
import com.furkan.gameapi.exception.PlayerNotFoundException;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    private WeaponService weaponService;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getPlayerById_shouldReturnPlayer_whenPlayerExists() {
        Player player = new Player("Mage",100);
        player.setId(1);

        when(playerRepository.findById(1))
                .thenReturn(Optional.of(player));

        Player result = playerService.getPlayerById(1);

        assertEquals("Mage", result.getName());
        assertEquals(100, result.getHealth());
    }

    @Test
    void getPlayerById_shouldThrowException_whenPlayerDoesNotExist() {
        when(playerRepository.findById(3))
                .thenReturn(Optional.empty());

        assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayerById(3));
    }

    @Test
    void healPlayer_shouldIncreaseHealth_whenAmountIsValid() {
        Player player = new Player("Mage", 100);
        player.setId(1);

        when(playerRepository.findById(1))
                .thenReturn(Optional.of(player));

        Player result = playerService.healPlayer(1, 50);

        verify(playerRepository, times(1)).findById(1);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void healPlayer_shouldThrowException_whenAmountIsInvalid() {

        assertThrows(
                IllegalArgumentException.class,
                () -> playerService.healPlayer(1, -1)
        );

        verifyNoInteractions(playerRepository);
    }

    @Test
    void patchPlayer_shouldUpdateOnlyName_whenOnlyNameIsProvided() {
        PlayerPatchRequest request = new PlayerPatchRequest();

        Player player = new Player("Mage", 100);
        player.setId(1);

        request.setName("Arch Mage");

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        Player result = playerService.patchPlayer(1,request);

        assertEquals(100, result.getHealth());
        assertEquals("Arch Mage", result.getName());

        verify(playerRepository, times(1)).findById(1);
        verify(playerRepository, never()).save(any(Player.class));
    }
}

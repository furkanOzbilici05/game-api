package com.furkan.gameapi.controller;

import com.furkan.gameapi.exception.PlayerNotFoundException;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.service.CustomUserDetailsService;
import com.furkan.gameapi.service.JwtService;
import com.furkan.gameapi.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void getPlayerById_shouldReturn200AndPlayer_whenPlayerExists() throws Exception {
        Player player = new Player("Mage",100);
        player.setId(1);

        when(playerService.getPlayerById(1)).thenReturn(player);

        mockMvc.perform(get("/players/1")).andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Mage"))
                        .andExpect(jsonPath("$.health").value(100));
    }

    @Test
    void getPlayerById_shouldReturn404_whenPlayerDoesNotExist() throws Exception {
        when(playerService.getPlayerById(99))
                .thenThrow(new PlayerNotFoundException("Player not found"));

        mockMvc.perform(get("/players/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Player not found"));
    }

    @Test
    @WithMockUser
    void createPlayer_shouldReturn400_whenNameIsBlank() throws Exception {
        String json = """
            {
              "name": "",
              "health": 100
            }
            """;

        mockMvc.perform(
                        post("/players")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Player name cannot be blank"));

        verify(playerService, never()).createPlayer(any());
    }

    @Test
    @WithMockUser
    void createPlayer_shouldReturn400_whenHealthIsNegative() throws Exception {
        String json = """
            {
              "name": "Mage",
              "health": -1
            }
            """;

        mockMvc.perform(post("/players").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.health").value("Health cannot be negative"));

        verify(playerService, never()).createPlayer(any());
    }

}

package com.furkan.gameapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.furkan.gameapi.exception.PlayerNotFoundException;
import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.service.CustomUserDetailsService;
import com.furkan.gameapi.service.JwtService;
import com.furkan.gameapi.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

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

    @Test
    void getPlayers_shouldReturnPaginatedPlayers() throws Exception {
        Player mage = new Player("Mage",100);
        mage.setId(1);

        Player warrior = new Player("Warrior",100);
        warrior.setId(2);

        PageRequest pageable = PageRequest.of(
                0,
                2,
                Sort.by(Sort.Direction.DESC, "health")
        );

        Page<Player> page = new PageImpl<>(
                List.of(mage,warrior),
                pageable,
                7
        );

        when(playerService.getAllPlayers(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/players")
                .param("page", "0")
                .param("size", "2")
                .param("sort","health,desc")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Mage"))
                .andExpect(jsonPath("$.content[0].health").value(100))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Warrior"))
                .andExpect(jsonPath("$.content[1].health").value(100))
                .andExpect(jsonPath("$.totalElements").value(7))
                .andExpect(jsonPath("$.totalPages").value(4));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(playerService).getAllPlayers(captor.capture());

        Pageable captured = captor.getValue();

        assertEquals(0,captured.getPageNumber());
        assertEquals(2,captured.getPageSize());

        Sort.Order order = captured.getSort().getOrderFor("health");
        assertNotNull(order);
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }
}

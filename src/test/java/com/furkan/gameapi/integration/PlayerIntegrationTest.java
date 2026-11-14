package com.furkan.gameapi.integration;

import com.furkan.gameapi.model.Player;
import com.furkan.gameapi.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlayerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setup() {
        playerRepository.deleteAll();
    }

    @Test
    void createPlayer_shouldSavePlayerAndReturn201() throws Exception {
        String json = """
                {
                    "name": "mage",
                    "health": 100
                }
                """;
        mockMvc.perform(post("/players")
                        .with(user("test-user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("mage"))
                .andExpect(jsonPath("$.health").value(100));

        Optional<Player> savedPlayer = playerRepository.findByName("mage");

        assertTrue(savedPlayer.isPresent());
        assertEquals("mage", savedPlayer.get().getName());
        assertEquals(100, savedPlayer.get().getHealth());

    }

    @Test
    void getPlayerById_shouldReturnPlayerFromDatabase() throws Exception {
        Player player = new Player("mage",100);
        playerRepository.save(player);
        Integer id = player.getId();

        mockMvc.perform(get("/players/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("mage"))
                .andExpect(jsonPath("$.health").value(100));
    }

    @Test
    void getPlayerById_shouldReturn404_whenPlayerDoesNotExist() throws Exception {
        mockMvc.perform(get("/players/{id}", 9999))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Player with id " + 9999 + " not found"));
    }

    @Test
    void createPlayer_shouldReturn400AndNotSave_whenHealthIsNegative() throws Exception {
        String json
                = """
                {
                    "name": "mage",
                    "health": -1
                }
                """;
        mockMvc.perform(post("/players")
                .with(user("test-user"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.health").value("Health cannot be negative"));

        assertTrue(playerRepository.findByName("mage").isEmpty());
    }
}

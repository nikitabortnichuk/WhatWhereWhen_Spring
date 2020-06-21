package com.bortni.service;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import com.bortni.model.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @MockBean
    GameRepository gameRepository;

    @Test
    public void shouldCallSaveGame_WhenSaveGame() {
        gameService.save(getGames().get(0));
        verify(gameRepository).save(any());
    }

    @Test
    public void shouldGetById_WhenFindByIdentification() {
        when(gameRepository.findByGameIdentification(anyString())).thenReturn(Optional.of(getGames().get(0)));

        Game expected = getGames().get(0);
        Game actual = gameService.findByIdentification("anyGameId");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetGameListByUserId_WhenFindByUserId() {
        when(gameRepository.findByUserId(anyLong())).thenReturn(new HashSet<>(getGames()));

        List<Game> expected = getGames();
        List<Game> actual = gameService.findByUserId(1L);

        assertEquals(expected, actual);
    }

    private List<Game> getGames() {
        List<Game> gameList = new ArrayList<>();
        gameList.add(
                Game.builder()
                        .id(1L)
                        .gameIdentification("gameId1")
                        .build()
        );
        gameList.add(
                Game.builder()
                        .id(2L)
                        .gameIdentification("gameId2")
                        .build()
        );
        gameList.add(
                Game.builder()
                        .id(3L)
                        .gameIdentification("gameId3")
                        .build()
        );
        return gameList;
    }

}

package com.bortni.service;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;
import com.bortni.model.entity.User;
import com.bortni.model.entity.question.Question;
import com.bortni.model.exception.MyEntityNotFoundException;
import com.bortni.model.repository.GameRepository;
import com.bortni.service.util.GameStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final GameStringGenerator gameStringGenerator;

    @Autowired
    public GameService(GameRepository gameRepository, GameStringGenerator gameStringGenerator) {
        this.gameRepository = gameRepository;
        this.gameStringGenerator = gameStringGenerator;
    }

    public void save(Game game) {
        String gameId = gameStringGenerator.generate();
        game.setGameIdentification(gameId);
        game.setAvailable(true);
        gameRepository.save(game);
        LOGGER.debug("Game saved");
    }

    public void update(Game game) {
        gameRepository.save(game);
    }

    public Game findByIdentification(String identification) {
        LOGGER.info("Searching for game");
        return gameRepository.findByGameIdentification(identification).orElseThrow(() ->
                new MyEntityNotFoundException("Game not found")
        );
    }

    public List<Game> findByUserId(Long id) {
        LOGGER.info("Searching for gameList by userId");
        return new ArrayList<>(gameRepository.findByUserId(id));
    }

    public Page<Game> findByUserId(long id, Pageable pageable) {
        LOGGER.info("Searching for gameList by userId paged");
        return gameRepository.findByUserId(id, pageable);
    }
}

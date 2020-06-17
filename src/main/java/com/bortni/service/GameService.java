package com.bortni.service;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;
import com.bortni.model.entity.User;
import com.bortni.model.entity.question.Question;
import com.bortni.model.repository.GameRepository;
import com.bortni.service.util.GameStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

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
    }

    public void update(Game game) {
        gameRepository.save(game);

    }

    public Game findByIdentification(String identification) {
        return gameRepository.findByGameIdentification(identification);
    }

    public List<Game> findByUserId(Long id) {
        return new ArrayList<>(gameRepository.findByUserId(id));
    }

    public void saveUserToGame(User user, Game game) {
        game.getUsers().add(user);
        save(game);
    }

    public void saveQuestionToGame(Question question, Game game) {
        game.getQuestions().add(question);
        save(game);
    }

    public void saveStatisticsToGame(Statistics statistics, Game game) {
        game.setStatistics(statistics);
        save(game);
    }

}

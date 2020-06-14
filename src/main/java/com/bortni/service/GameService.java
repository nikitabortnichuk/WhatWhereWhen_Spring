package com.bortni.service;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;
import com.bortni.model.entity.User;
import com.bortni.model.entity.question.Question;
import com.bortni.model.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void save(Game game){
        gameRepository.save(game);
    }

    public List<Game> findByUserId(Long id){
        return new ArrayList<>(gameRepository.findByUserId(id));
    }

    public void saveUserToGame(User user, Game game){
        game.getUsers().add(user);
        save(game);
    }

    public void saveQuestionToGame(Question question, Game game) {
        game.getQuestions().add(question);
        save(game);
    }

    public void saveStatisticsToGame(Statistics statistics, Game game){
        game.setStatistics(statistics);
        save(game);
    }

}

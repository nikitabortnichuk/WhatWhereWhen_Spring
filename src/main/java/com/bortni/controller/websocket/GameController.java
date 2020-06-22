package com.bortni.controller.websocket;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.websocket.ChatMessage;
import com.bortni.model.websocket.GameMessage;
import com.bortni.model.websocket.MessageType;
import com.bortni.service.GameService;
import com.bortni.service.QuestionService;
import com.bortni.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;
    private final GameService gameService;
    private final QuestionService questionService;
    private final UserService userService;

    private static Map<String, List<Question>> questionMap = new HashMap<>();
    private static final Map<String, List<Boolean>> answerMap = new HashMap<>();
    private static final Map<String, List<Question>> actualQuestionMap = new HashMap<>();

    @Autowired
    public GameController(SimpMessageSendingOperations simpMessageSendingOperations, SimpUserRegistry simpUserRegistry, GameService gameService, QuestionService questionService, UserService userService) {
        this.messagingTemplate = simpMessageSendingOperations;
        this.simpUserRegistry = simpUserRegistry;
        this.gameService = gameService;
        this.questionService = questionService;
        this.userService = userService;
    }

    @GetMapping("/game/{gameId}")
    public String gamePage(@PathVariable String gameId) {
        Game game = gameService.findByIdentification(gameId);
        if (game.isAvailable()) {
            return "game";
        }
        else {
            return "redirect:/home?error=true";
        }
    }

    @MessageMapping("/game/{gameId}/sendMessage")
    public void sendMessage(@DestinationVariable String gameId, @Payload ChatMessage chatMessage) {
        LOGGER.info("Sending chat message");
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), chatMessage);
    }

    @MessageMapping("/game/{gameId}/addUser")
    public void addUser(@DestinationVariable String gameId, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        LOGGER.info("User connected");
        String currentGameId = (String) headerAccessor.getSessionAttributes().put("gameId", gameId);
        if (currentGameId != null) {
            sendLeaveMessage(chatMessage, currentGameId);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        Set<String> usernameSet = getUsersBySubscription(gameId);
        chatMessage.setUsernameSet(usernameSet);
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), chatMessage);

        if (isUsersNumberMatchConfig(gameId, usernameSet.size())) {
            addToQuestionMap(gameId);
            sendStartMessage(gameId);
            LOGGER.debug("Game started");
        }
    }

    private void addToQuestionMap(String gameId) {
        List<Question> questionList = questionMap.get(gameId);
        if (questionList == null) {
            Game game = gameService.findByIdentification(gameId);
            int roundsNumber = game.getConfiguration().getRoundsNumber();
            questionList = questionService.findNRandomQuestions(roundsNumber);
            questionMap.put(gameId, questionList);
        }
    }

    private void sendStartMessage(@DestinationVariable String gameId) {
        ChatMessage startMessage = new ChatMessage();
        startMessage.setType(MessageType.START);
        startMessage.setContent("GAME STARTED!");
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), startMessage);
    }

    @MessageMapping("/game/{gameId}/sendQuestion")
    public void processQuestion(@DestinationVariable String gameId, @Payload GameMessage gameMessage) {
        int roundNumber = gameMessage.getRoundNumber();

        synchronized (actualQuestionMap) {
            List<Question> actualQuestionList = actualQuestionMap.get(gameId);

            if (actualQuestionList == null) {
                actualQuestionList = new ArrayList<>();
                actualQuestionMap.put(gameId, actualQuestionList);
            } else if (actualQuestionList.isEmpty()) {
                sendQuestion(gameId, gameMessage, 0);
            } else if (actualQuestionList.size() == roundNumber) {
                sendQuestion(gameId, gameMessage, roundNumber);
            }
        }
    }

    private void sendQuestion(String gameId, GameMessage gameMessage, int roundNumber) {

        Game game = gameService.findByIdentification(gameId);
        int roundTime = game.getConfiguration().getRoundTime();
        int roundsNumberCount = game.getConfiguration().getRoundsNumber();

        if (roundNumber < roundsNumberCount) {
            Question question = questionMap.get(gameId).get(roundNumber);
            List<Question> actualQuestionList = actualQuestionMap.get(gameId);
            actualQuestionList.add(question);
            sendAskMessage(gameId, gameMessage, question, roundTime);
            LOGGER.debug("Sending question content");
        } else {
            processGameEnding(gameId);
            sendEndMessage(gameId);
            LOGGER.debug("Game ended");
        }
    }

    private void sendAskMessage(String gameId, GameMessage gameMessage, Question question, int roundTime) {
        gameMessage.setType(MessageType.ASK);
        gameMessage.setQuestion(question);
        gameMessage.setRoundTime(roundTime);
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), gameMessage);
    }

    private void processGameEnding(String gameId) {
        Game game = gameService.findByIdentification(gameId);

        int correctAnswers = getIsCorrectNumberCount(gameId, true);
        int incorrectAnswers = getIsCorrectNumberCount(gameId, false);

        game.setAvailable(false);
        Statistics statistics = new Statistics();
        statistics.setExpertScore(correctAnswers);
        statistics.setOpponentScore(incorrectAnswers);
        game.setStatistics(statistics);

        game.setUsers(getUsersBySubscription(gameId).stream().map(userService::findByUsername).collect(Collectors.toSet()));
        game.setQuestions(new HashSet<>(questionMap.get(gameId)));

        gameService.update(game);

    }

    private void sendEndMessage(String gameId) {
        GameMessage gameMessage = new GameMessage();
        gameMessage.setType(MessageType.END);
        gameMessage.setCorrectAnswers(getIsCorrectNumberCount(gameId, true));
        gameMessage.setIncorrectAnswers(getIsCorrectNumberCount(gameId, false));
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), gameMessage);
    }

    private int getIsCorrectNumberCount(String gameId, boolean isCorrect) {
        List<Boolean> isCorrectAnswerList = answerMap.get(gameId);
        return (int) isCorrectAnswerList.stream().filter(b -> b == isCorrect).count();
    }

    @MessageMapping("/game/{gameId}/sendAnswer")
    public void sendAnswer(@DestinationVariable String gameId, @Payload GameMessage gameMessage) {
        List<Question> questionList = questionMap.get(gameId);

        int roundNumber = gameMessage.getRoundNumber();
        Question question = questionList.get(roundNumber);

        String actualAnswer = gameMessage.getAnswer();
        String correctAnswer = getCorrectAnswer(question);

        sendAnswerMessage(gameId, gameMessage, actualAnswer, correctAnswer);
        LOGGER.debug("Sending answer message");
    }

    private void sendAnswerMessage(String gameId, GameMessage gameMessage, String actualAnswer, String correctAnswer) {

        String message;
        if (actualAnswer.equalsIgnoreCase(correctAnswer)) {
            message = "correct";
            sendAnswerIsCorrect(gameId, gameMessage, message, true);
            LOGGER.debug("Sending correct message");
        } else if (actualAnswer.equals("")) {
            sendTimeIsOver(gameId, gameMessage);
            LOGGER.debug("Time to answer is over");
        } else {
            message = "incorrect";
            sendAnswerIsCorrect(gameId, gameMessage, message, false);
            LOGGER.debug("Sending incorrect message");
        }

    }

    private void sendAnswerIsCorrect(String gameId, GameMessage gameMessage, String message, boolean isCorrect) {
        List<Boolean> isCorrectAnswers = answerMap.computeIfAbsent(gameId, k -> new ArrayList<>());
        isCorrectAnswers.add(isCorrect);
        gameMessage.setIsCorrect(false);
        gameMessage.setMessage(message);
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), gameMessage);
    }

    private void sendTimeIsOver(String gameId, GameMessage gameMessage){
        String message = "timeIsOver";
        synchronized (answerMap) {
            List<Boolean> isCorrectAnswers = answerMap.get(gameId);
            if (isCorrectAnswers == null) {
                isCorrectAnswers = new ArrayList<>();
                answerMap.put(gameId, isCorrectAnswers);
            } else if (isCorrectAnswers.size() == gameMessage.getRoundNumber()) {
                isCorrectAnswers.add(false);
                gameMessage.setIsCorrect(false);
                gameMessage.setMessage(message);
                messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), gameMessage);
            }
        }
    }

    private String getCorrectAnswer(Question question) {
        String correctAnswer;
        switch (question.getQuestionType()) {
            case NO_VARIANTS:
                correctAnswer = question.getAnswer();
                break;
            case WITH_VARIANTS:
                correctAnswer = question.getVariantList().stream()
                        .filter(Variant::getCorrect)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("There is no correct answer"))
                        .getText();
                break;
            default:
                correctAnswer = "";
        }
        return correctAnswer;
    }

    private void sendLeaveMessage(@Payload ChatMessage chatMessage, String currentGameId) {
        LOGGER.info("User disconnected");
        ChatMessage leaveMessage = new ChatMessage();
        leaveMessage.setType(MessageType.LEAVE);
        leaveMessage.setSender(chatMessage.getSender());
        leaveMessage.setContent("Disconnected");
        messagingTemplate.convertAndSend(String.format("/channel/%s", currentGameId), leaveMessage);
    }

    private Set<String> getUsersBySubscription(@DestinationVariable String gameId) {
        return simpUserRegistry
                .findSubscriptions(subscription -> subscription
                        .getDestination()
                        .contains(String.format("/channel/%s", gameId)))
                .stream()
                .map(subscription -> subscription.getSession().getUser().getName())
                .collect(Collectors.toSet());
    }

    private boolean isUsersNumberMatchConfig(String gameId, int currentUsersNumber) {
        Game game = gameService.findByIdentification(gameId);
        return game.getConfiguration().getPlayersNumber() == currentUsersNumber;
    }
}

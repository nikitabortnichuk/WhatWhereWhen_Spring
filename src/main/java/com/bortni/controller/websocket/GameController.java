package com.bortni.controller.websocket;

import com.bortni.model.websocket.ChatMessage;
import com.bortni.model.websocket.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class GameController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    @Autowired
    public GameController(SimpMessageSendingOperations simpMessageSendingOperations, SimpUserRegistry simpUserRegistry) {
        this.messagingTemplate = simpMessageSendingOperations;
        this.simpUserRegistry = simpUserRegistry;
    }

    @GetMapping("/game/{gameId}")
    public String gamePage(@PathVariable String gameId){
        return "game";
    }

    @MessageMapping("/game/{gameId}/sendMessage")
    public void sendMessage(@DestinationVariable String gameId, @Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), chatMessage);
    }

    @MessageMapping("/game/{gameId}/addUser")
    public void addUser(@DestinationVariable String gameId, @Payload ChatMessage chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentGameId = (String) headerAccessor.getSessionAttributes().put("gameId", gameId);
        if(currentGameId != null) {
            ChatMessage leaveMessage = new ChatMessage();
            leaveMessage.setType(MessageType.LEAVE);
            leaveMessage.setSender(chatMessage.getSender());
            leaveMessage.setContent("Disconnected");
            messagingTemplate.convertAndSend(String.format("/channel/%s", currentGameId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        Set<String> usernameSet = getUsersBySubscription(gameId);
        chatMessage.setUsernameSet(usernameSet);

        messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), chatMessage);
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
}

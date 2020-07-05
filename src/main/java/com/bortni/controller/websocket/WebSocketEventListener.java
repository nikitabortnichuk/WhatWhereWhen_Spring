package com.bortni.controller.websocket;

import com.bortni.model.websocket.ChatMessage;
import com.bortni.model.websocket.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        LOGGER.info("Received a new web socket connection.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String gameId = (String) headerAccessor.getSessionAttributes().get("gameId");
        if (username != null) {
            LOGGER.info(username + ": disconnected");

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSender(username);
            chatMessage.setType(MessageType.LEAVE);
            chatMessage.setContent("Disconnected");

            messagingTemplate.convertAndSend(String.format("/channel/%s", gameId), chatMessage);
        }
    }
}

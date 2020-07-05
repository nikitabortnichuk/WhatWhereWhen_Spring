package com.bortni.model.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {

    private String sender;
    private MessageType type;
    private String content;

    private Set<String> usernameSet;

}

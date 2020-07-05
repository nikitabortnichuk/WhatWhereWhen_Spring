package com.bortni.model.websocket;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameMessage {

    private MessageType type;
    private Integer roundNumber;
    private Boolean isCorrect;
    private String message;
    private Integer correctAnswers;
    private Integer incorrectAnswers;
    private Integer roundTime;
    private Question question;
    private String answer;

}

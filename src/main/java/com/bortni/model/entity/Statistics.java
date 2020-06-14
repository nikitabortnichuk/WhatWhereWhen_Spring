package com.bortni.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Statistics {

    private int expertScore;
    private int opponentScore;

    private int numberOfUsedHints;
    private int averageTimePerRound;
    private int averageScorePerRound;

}

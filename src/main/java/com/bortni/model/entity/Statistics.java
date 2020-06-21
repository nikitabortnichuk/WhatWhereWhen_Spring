package com.bortni.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Embeddable
public class Statistics {

    @Column(nullable = true)
    private int expertScore;
    @Column(nullable = true)
    private int opponentScore;

    @Column(nullable = true)
    private int numberOfUsedHints;
    @Column(nullable = true)
    private int averageTimePerRound;
    @Column(nullable = true)
    private int averageScorePerRound;

}

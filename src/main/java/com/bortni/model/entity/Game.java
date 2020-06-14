package com.bortni.model.entity;

import com.bortni.model.entity.question.Question;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"questions", "users"})
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "game_identification")
    private String gameIdentification;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "questions_games",
            inverseJoinColumns = @JoinColumn(name = "game_id"),
            joinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions;

    @Embedded
    private Statistics statistics;
    @Embedded
    private Configuration configuration;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "users_games",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    private boolean isAvailable;

}

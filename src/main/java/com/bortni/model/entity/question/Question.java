package com.bortni.model.entity.question;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Variant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
@Table(name = "questions")
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String questionText;

    @Column(name = "question_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    private Set<Game> games;

    public abstract List<Variant> getVariantList();

    public abstract String getAnswer();

}

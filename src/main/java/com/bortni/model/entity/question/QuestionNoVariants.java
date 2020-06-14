package com.bortni.model.entity.question;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@DiscriminatorValue("NO_VARIANTS")
public class QuestionNoVariants extends Question {

    @NotNull
    @Column(nullable = false)
    private String answer;

    public QuestionNoVariants(long id, String questionText, QuestionType questionType, Set<Game> games, String answer){
        super(id, questionText, questionType, games);
        this.answer = answer;
    }

    @Override
    public List<Variant> getVariantList() {
        return new ArrayList<>();
    }
}

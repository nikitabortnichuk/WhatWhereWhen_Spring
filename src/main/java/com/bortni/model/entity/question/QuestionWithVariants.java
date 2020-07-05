package com.bortni.model.entity.question;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.Variant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "variantList")
@Entity
@DiscriminatorValue("WITH_VARIANTS")
public class QuestionWithVariants extends Question {

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Variant> variantList;

    public QuestionWithVariants(long id, String questionText, QuestionType questionType, Set<Game> games, List<Variant> variantList){
        super(id, questionText, questionType, games);
        this.variantList = variantList;
    }

    @Override
    public String getAnswer() {
        return "";
    }
}

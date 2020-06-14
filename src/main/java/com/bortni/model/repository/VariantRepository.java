package com.bortni.model.repository;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.QuestionWithVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {

    List<Variant> findByQuestion(QuestionWithVariants question);

}

package com.bortni.service;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.QuestionWithVariants;
import com.bortni.model.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariantService {

    private final VariantRepository variantRepository;

    @Autowired
    public VariantService(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
    }

    public void save(Variant variant) {
        variantRepository.save(variant);
    }

    public void update(Variant variant) {
        variantRepository.save(variant);
    }

    public List<Variant> findByQuestion(QuestionWithVariants question){
        return variantRepository.findByQuestion(question);
    }
}

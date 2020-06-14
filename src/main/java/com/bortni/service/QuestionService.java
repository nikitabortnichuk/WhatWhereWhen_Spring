package com.bortni.service;

import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionWithVariants;
import com.bortni.model.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).get();//todo exception
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void update(Question question) {
        questionRepository.save(question);
    }

    public void delete(long id) {
        questionRepository.deleteById(id);
    }

    public List<Question> findNRandomQuestions(int questionsNumber) {
        List<Question> allQuestions = findAll();
        List<Question> randomQuestions = new ArrayList<>();

        int size = allQuestions.size();
        Random random = new Random();
        random.ints(0, size)
                .distinct()
                .limit(questionsNumber)
                .forEach(n -> randomQuestions.add(allQuestions.get(n)));
        return randomQuestions;
    }

    public void setIsCorrect(String value, QuestionWithVariants questionWithVariants) {
        switch (value) {
            case "1":
                questionWithVariants.getVariantList().get(0).setCorrect(true);
                break;
            case "2":
                questionWithVariants.getVariantList().get(1).setCorrect(true);
                break;
            case "3":
                questionWithVariants.getVariantList().get(2).setCorrect(true);
                break;
            case "4":
                questionWithVariants.getVariantList().get(3).setCorrect(true);
                break;
        }
    }
}

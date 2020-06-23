package com.bortni.service;

import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionWithVariants;
import com.bortni.model.exception.MyEntityNotFoundException;
import com.bortni.model.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        LOGGER.info("Searching for question list");
        return questionRepository.findAll();
    }

    public Question findById(Long id) {
        LOGGER.info("Searching for question by id");
        return questionRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Question not found"));
    }

    public void save(Question question) {
        questionRepository.save(question);
        LOGGER.debug("Question saved");
    }

    public void delete(long id) {
        questionRepository.deleteById(id);
        LOGGER.debug("Question deleted");
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
        LOGGER.debug("Getting N random questions");
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

    public Page<Question> findAll(Pageable pageable) {
        LOGGER.info("Searching for question list paged");
        return questionRepository.findAll(pageable);
    }
}

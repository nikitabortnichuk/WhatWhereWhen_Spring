package com.bortni.service;

import com.bortni.model.repository.QuestionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @MockBean
    QuestionRepository questionRepository;

}

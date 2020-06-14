package com.bortni.controller;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionNoVariants;
import com.bortni.model.entity.question.QuestionType;
import com.bortni.model.entity.question.QuestionWithVariants;
import com.bortni.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final QuestionService questionService;

    @Autowired
    public AdminController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String signInPage(){
        return "admin/sign_in_admin";
    }

    @PostMapping(value = "sign-in")
    public String signIn(){

        return "redirect:";
    }

    @GetMapping(value = "/show-questions")
    public String showQuestions(Model model){

        List<Question> questionList = questionService.findAll();

        model.addAttribute("questionList", questionList);
        return "admin/show_questions";
    }

    @GetMapping(value = "/add-question")
    public String addQuestionPage(Model model){
        model.addAttribute("question", new QuestionNoVariants());
        return "admin/add_question";
    }

    @PostMapping(value = "/add-question")
    public String addWithOrWithoutVariantsPage(@RequestParam String questionType, Model model) {

        QuestionType type = QuestionType.valueOf(questionType);

        switch (type){
            case WITH_VARIANTS:
                QuestionWithVariants questionWithVariants = new QuestionWithVariants();
                questionWithVariants.setQuestionType(QuestionType.WITH_VARIANTS);
                model.addAttribute("questionWithVariants", questionWithVariants);
                return "admin/add_question_variants";
            case NO_VARIANTS:
                QuestionNoVariants questionNoVariants = new QuestionNoVariants();
                questionNoVariants.setQuestionType(QuestionType.NO_VARIANTS);
                model.addAttribute("questionNoVariants", questionNoVariants);
                return "admin/add_question_answer";
        }

        return "admin/add_question";
    }

    @PostMapping(value = "/add-question-answer")
    public String addQuestionAnswer(@ModelAttribute @Valid QuestionNoVariants questionNoVariants, Model model){

        questionService.save(questionNoVariants);

        return "redirect:/admin/show-questions";
    }

    @PostMapping(value = "/add-question-variants")
    public String addQuestionVariants(@RequestParam String isCorrect,
                                      @ModelAttribute @Valid QuestionWithVariants questionWithVariants,
                                      Model model){

        questionService.setIsCorrect(isCorrect, questionWithVariants);

        List <Variant> variantList = new ArrayList<>();
        for (Variant variant : questionWithVariants.getVariantList()) {
            variant.setQuestion(questionWithVariants);
            variantList.add(variant);
        }
        questionWithVariants.setVariantList(variantList);
        questionService.save(questionWithVariants);

        return "redirect:/admin/show-questions";
    }

    @PostMapping(value = "/delete")
    public String delete(@RequestParam long questionId) {

        questionService.delete(questionId);

        return "redirect:/admin/show-questions";
    }

    @GetMapping(value = "/edit")
    public String editPage(@RequestParam long questionId, Model model) {

        Question questionToUpdate = questionService.findById(questionId);

        switch (questionToUpdate.getQuestionType()){
            case WITH_VARIANTS:
                QuestionWithVariants questionWithVariants = new QuestionWithVariants(
                        questionToUpdate.getId(), questionToUpdate.getQuestionText(),
                        questionToUpdate.getQuestionType(), questionToUpdate.getGames(),
                        questionToUpdate.getVariantList()
                );
                model.addAttribute("questionWithVariants", questionWithVariants);
                return "admin/add_question_variants";
            case NO_VARIANTS:
                QuestionNoVariants questionNoVariants = new QuestionNoVariants(
                        questionToUpdate.getId(), questionToUpdate.getQuestionText(),
                        questionToUpdate.getQuestionType(), questionToUpdate.getGames(),
                        questionToUpdate.getAnswer()
                );
                model.addAttribute("questionNoVariants", questionNoVariants);
                return "admin/add_question_answer";
        }

        return "admin/add_question";
    }
}

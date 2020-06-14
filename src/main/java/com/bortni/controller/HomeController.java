package com.bortni.controller;

import com.bortni.service.GameService;
import com.bortni.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String home(){
        return "home";
    }

}

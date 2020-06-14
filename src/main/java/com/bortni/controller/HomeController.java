package com.bortni.controller;

import com.bortni.service.GameService;
import com.bortni.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping(value = {"/", "/home"})
    public String home(){
        return "home";
    }

    @GetMapping(value = "/sign-in")
    public String signIn(){
        return "sign_in";
    }

    @GetMapping(value = "/sign-up")
    public String signUp(){
        return "sign_up";
    }

}

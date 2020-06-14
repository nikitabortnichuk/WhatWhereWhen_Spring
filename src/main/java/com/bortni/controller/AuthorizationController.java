package com.bortni.controller;

import com.bortni.model.entity.User;
import com.bortni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class AuthorizationController {

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/sign-in")
    public String signInPage(){
        return "sign_in";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "sign_up";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute @Valid User user){

        userService.save(user);
        return "redirect:/sign-in";
    }
}

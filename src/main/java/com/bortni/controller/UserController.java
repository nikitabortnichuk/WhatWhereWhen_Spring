package com.bortni.controller;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import com.bortni.service.GameService;
import com.bortni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public UserController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        List<Game> gameList = gameService.findByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("gameList", gameList);
        return "profile";
    }
}

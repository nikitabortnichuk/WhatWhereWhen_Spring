package com.bortni.controller;

import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.service.GameService;
import com.bortni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public HomeController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        Game game = new Game();
        model.addAttribute("game", game);
        return "home";
    }

    @GetMapping(value = "/sign-in")
    public String signInPage() {
        return "sign_in";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign_up";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute @Valid User user) {
        userService.save(user);
        return "redirect:/sign-in";
    }

    @PostMapping(value = "/find-game")
    public String findGame(@RequestParam @NotNull String gameId, HttpSession httpSession, Principal principal) {

        try {
            Game game = gameService.findByIdentification(gameId);
            httpSession.setAttribute("gameId", game.getGameIdentification());
            httpSession.setAttribute("username", principal.getName());

            return "redirect:/game/" + gameId;
        } catch (EntityNotFoundException e) {
            return "redirect:/home?error=true";
        }
    }

    @PostMapping(value = "/create-game")
    public String createGame(@ModelAttribute @Valid Game game, HttpSession httpSession, Principal principal) {

        gameService.save(game);
        String gameId = game.getGameIdentification();
        httpSession.setAttribute("gameId", gameId);
        httpSession.setAttribute("username", principal.getName());
        return "redirect:/game/" + gameId;
    }

}

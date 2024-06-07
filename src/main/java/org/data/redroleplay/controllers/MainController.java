package org.data.redroleplay.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "redirect:/";
    }
}

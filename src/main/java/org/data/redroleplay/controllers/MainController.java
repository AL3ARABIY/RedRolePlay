package org.data.redroleplay.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/")
    public String home() {
        return "pages/home";
    }
}

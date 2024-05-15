package org.data.redroleplay.controllers;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.services.implementations.ProfileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProfileService profileService;
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
        profileService.getUserProfile(SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }
}

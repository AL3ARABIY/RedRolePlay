package org.data.redroleplay.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.services.UserService;
import org.data.redroleplay.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserService userService;

    private final UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(
            @Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
            BindingResult result,
            Model model
    ) {

        if(result.hasErrors()) {
            return "registration";
        }

        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}

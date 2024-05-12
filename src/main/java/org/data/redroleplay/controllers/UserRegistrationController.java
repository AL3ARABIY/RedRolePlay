package org.data.redroleplay.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.configurations.DiscordConfiguration;
import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.services.DiscordDataExtractorService;
import org.data.redroleplay.services.UserService;
import org.data.redroleplay.services.implementations.DiscordDataExtractorServiceImpl.DiscordUser;
import org.data.redroleplay.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserService userService;

    private final UserValidator userValidator;

    private final DiscordConfiguration discordConfiguration;

    private final DiscordDataExtractorService discordDataExtractorService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/discord")
    public String showRegistrationForm(Model model) {

        String authorizationUri = discordConfiguration.getAuthorizationUri();

        model.addAttribute("authorizationUri", authorizationUri);

        model.addAttribute("ShowDiscordButton", true);

        return "registration";
    }

    @GetMapping
    public String getDiscordInfo(@RequestParam("code") String accessToken , Model model) {

        Optional<DiscordUser> data = discordDataExtractorService.getDiscordInfo(accessToken);

        data.ifPresentOrElse(discordUser -> {
            UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
            userRegistrationDto.setEmail(discordUser.getEmail());
            userRegistrationDto.setDiscordId(discordUser.getId());
            model.addAttribute("ShowRegistrationForm", true);
            model.addAttribute("user", userRegistrationDto);
            String avatarUrl = String.format("https://cdn.discordapp.com/avatars/%s/%s", discordUser.getId(), discordUser.getAvatar());
            model.addAttribute("AvatarUrl", avatarUrl);
        }, () -> {
            model.addAttribute("ShowFailingMessage", true);
        });

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

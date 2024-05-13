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
    public String showDiscordLink(Model model) {

        String authorizationUri = discordConfiguration.getAuthorizationUri();

        model.addAttribute("authorizationUri", authorizationUri);

        model.addAttribute("ShowDiscordButton", true);

        return "pages/registration";
    }

    @GetMapping
    public String getDiscordInfo(@RequestParam("code") String accessToken , Model model) {

        Optional<DiscordUser> data = discordDataExtractorService.getDiscordInfo(accessToken);

        data.ifPresentOrElse(discordUser -> {

            UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                    .email(discordUser.getEmail())
                    .discordId(discordUser.getId())
                    .discordUsername(discordUser.getUsername())
                    .discordAvatar(String.format("https://cdn.discordapp.com/avatars/%s/%s", discordUser.getId(), discordUser.getAvatar()))
                    .build();

            model.addAttribute("ShowRegistrationForm", true);
            model.addAttribute("user", userRegistrationDto);

        }, () -> {
            model.addAttribute("ShowFailingMessage", true);
        });

        return "pages/registration";
    }

    @PostMapping
    public String registerUserAccount(
            @Valid @ModelAttribute("user") UserRegistrationDto registrationDto,
            BindingResult result,
            Model model
    ) {

        model.addAttribute("ShowRegistrationForm", true);

        if(result.hasErrors()) {
            return "pages/registration";
        }

        userService.save(registrationDto);
        return "redirect:/login?successRegistration";
    }
}

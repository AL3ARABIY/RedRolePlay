package org.data.redroleplay.validators;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserRegistrationDto user = (UserRegistrationDto) target;

        if (userService.existsByMtaUsername(user.getMtaUsername())) {
            errors.rejectValue("mtaUsername", "Duplicate.user.mtaUsername");
        }

        if (userService.existsByEmail(user.getEmail())) {
            errors.rejectValue("email", "Duplicate.user.email");
        }

        if (userService.existsByDiscordId(user.getDiscordId())) {
            errors.rejectValue("discordId", "Duplicate.user.discordId");
        }

        if (userService.existsByMtaSerial(user.getMtaSerial())) {
            errors.rejectValue("mtaSerial", "Duplicate.user.mtaSerial");
        }
    }
}

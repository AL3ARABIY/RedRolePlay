package org.data.redroleplay.validators;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MtaSerialValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String mtaSerial = (String) target;

        if (Boolean.TRUE.equals(userService.existsByMtaSerial(mtaSerial))) {
            errors.rejectValue("mtaSerial", "Duplicate.user.mtaSerial");
        }
    }
}

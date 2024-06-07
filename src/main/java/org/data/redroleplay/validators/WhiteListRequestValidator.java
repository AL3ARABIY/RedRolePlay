package org.data.redroleplay.validators;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whitelistrequest.WhitelistRequestDto;
import org.data.redroleplay.services.WhitelistRequestService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class WhiteListRequestValidator implements Validator {

    private final WhitelistRequestService whitelistRequestService;

    @Override
    public boolean supports(Class<?> clazz) {
        return WhitelistRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WhitelistRequestDto whitelistRequestDto = (WhitelistRequestDto) target;

        if (whitelistRequestService.existsByCharacterFirstNameAndCharacterLastName(whitelistRequestDto.getCharacterFirstName(), whitelistRequestDto.getCharacterLastName())) {
            errors.rejectValue("characterFirstName", "Duplicate.whitelistRequest.characterFirstName");
            errors.rejectValue("characterLastName", "Duplicate.whitelistRequest.characterLastName");
        }
    }

}

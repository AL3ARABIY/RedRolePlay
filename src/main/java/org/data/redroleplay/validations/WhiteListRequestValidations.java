package org.data.redroleplay.validations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.repositories.website.WhitelistRequestRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WhiteListRequestValidations {

    private final WhitelistRequestRepository whitelistRequestRepository;


}

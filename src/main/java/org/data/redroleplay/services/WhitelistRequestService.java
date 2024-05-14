package org.data.redroleplay.services;

import org.data.redroleplay.dtos.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.WhitelistRequestDto;
import org.data.redroleplay.entities.website.WhitelistRequest;

public interface WhitelistRequestService {

    WhitelistRequest save(WhitelistRequestDto whitelistRequestDto);

    WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto);

}

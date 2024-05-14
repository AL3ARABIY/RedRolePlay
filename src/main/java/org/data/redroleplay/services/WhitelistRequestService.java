package org.data.redroleplay.services;

import org.data.redroleplay.dtos.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.WhitelistRequestDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.models.CustomPageResponse;

public interface WhitelistRequestService {

    WhitelistRequest save(WhitelistRequestDto whitelistRequestDto);

    WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto);

    CustomPageResponse<WhitelistRequest ,WhitelistRequest> getAllByUserId(Long userId, Integer page, Integer size);

}

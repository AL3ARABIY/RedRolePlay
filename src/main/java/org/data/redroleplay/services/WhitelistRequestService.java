package org.data.redroleplay.services;

import org.data.redroleplay.dtos.whiteListRequest.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WhitelistRequestService {

    WhitelistRequest save(WhitelistRequestDto whitelistRequestDto);

    WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto , Long id);

    Page<WhitelistRequest> getAll(Integer page, Integer size);

    Page<WhitelistRequest> getAllByUserId(Long userId, Integer page, Integer size);

    Optional<WhitelistRequest> getById(Long id);

    boolean existsByCharacterFirstNameAndCharacterLastName(String characterFirstName, String characterLastName);


}

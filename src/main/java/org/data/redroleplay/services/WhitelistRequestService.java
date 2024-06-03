package org.data.redroleplay.services;

import org.data.redroleplay.dtos.whiteListRequest.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WhitelistRequestService {

    WhitelistRequest save(WhitelistRequestDto whitelistRequestDto);

    WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto , Long id);

    Page<WhitelistRequest> getAll(Integer page, Integer size);

    Page<WhitelistRequest> getAllByUserId(Long userId, Integer page, Integer size);

    Optional<WhitelistRequest> getById(Long id);

    Page<WhitelistRequest> getAllByStatus(WhitelistRequestStatus status , Integer page, Integer size);

    Page<WhitelistRequest> getAllByUserIdAndStatus(Long userId , WhitelistRequestStatus status , Integer page, Integer size);

    Page<WhitelistRequest> getAllById(Long id , Integer page, Integer size);

    Page<WhitelistRequest> getAllByCharacterFullName(String characterFullName , Integer page, Integer size);

    Page<WhitelistRequest> getAllByUserFullName(String userFullName , Integer page, Integer size);

    boolean existsByCharacterFirstNameAndCharacterLastName(String characterFirstName, String characterLastName);

    boolean canAuthenticatedUserCreateRequest();
}

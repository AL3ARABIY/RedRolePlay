package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.WhitelistRequestDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthorisation;
import org.data.redroleplay.repositories.website.WhitelistRequestRepository;

import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.WhitelistRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WhitelistRequestServiceImpl implements WhitelistRequestService {

    private final WhitelistRequestRepository whitelistRequestRepository;

    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public WhitelistRequest save(WhitelistRequestDto requestDto) {

        WhitelistRequest whitelistRequest = modelMapper.map(requestDto, WhitelistRequest.class);

        whitelistRequest.setRequestDate(LocalDateTime.now());
        whitelistRequest.setStatus(WhitelistRequestStatus.PENDING);

        authenticationService.getAuthenticatedUser()
                .ifPresentOrElse(
                        whitelistRequest::setUser,
                        () -> {
                            throw new UserNeedAuthentication("You need to be authenticated to create a whitelist request");
                        }
                );

        return whitelistRequestRepository.save(whitelistRequest);
    }

    @Override
    public WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto) {

        WhitelistRequest fetchedWhitelistRequest = whitelistRequestRepository.findById(verifyWhitelistRequestDto.getId())
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        modelMapper.map(verifyWhitelistRequestDto, fetchedWhitelistRequest);

        return whitelistRequestRepository.save(fetchedWhitelistRequest);
    }
}

package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whiteListRequest.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.errorHandling.costums.ValidationException;
import org.data.redroleplay.repositories.website.WhitelistRequestRepository;

import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.UserService;
import org.data.redroleplay.services.WhitelistRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WhitelistRequestServiceImpl implements WhitelistRequestService {

    private final WhitelistRequestRepository whitelistRequestRepository;

    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper = new ModelMapper();

    private final UserService userService;

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

        if(whitelistRequestRepository.countByUserId(whitelistRequest.getUser().getId()) >= 5){
            throw new ValidationException("You can't have more than 5 whitelist requests");
        }

        return whitelistRequestRepository.save(whitelistRequest);
    }

    @Override
    public WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto , Long id) {

        WhitelistRequest fetchedWhitelistRequest = whitelistRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        if(fetchedWhitelistRequest.getStatus() != WhitelistRequestStatus.PENDING){
            throw new ValidationException("Whitelist request already verified");
        }

        authenticationService.getAuthenticatedUser()
                .ifPresentOrElse(
                        fetchedWhitelistRequest::setVerifiedBy
                        ,
                        () -> {
                            throw new UserNeedAuthentication("You need to be authenticated to verify a whitelist request");
                        }
                );

        fetchedWhitelistRequest.setVerifiedDate(LocalDateTime.now());

        modelMapper.map(verifyWhitelistRequestDto, fetchedWhitelistRequest);

        return whitelistRequestRepository.save(fetchedWhitelistRequest);
    }

    @Override
    public Optional<WhitelistRequest> getById(Long id){
        return whitelistRequestRepository.findById(id);
    }

    @Override
    public Page<WhitelistRequest> getAllByUserId(Long userId, Integer page, Integer size){
        if(!userService.existsById(userId)) throw new RecordNotFoundException("User not found");
        return whitelistRequestRepository.findAllByUserId(userId, PageRequest.of(page, size));
    }

    @Override
    public Page<WhitelistRequest> getAllByUserIdAndStatus(Long userId, WhitelistRequestStatus status, Integer page, Integer size){
        if(!userService.existsById(userId)) throw new RecordNotFoundException("User not found");
        return whitelistRequestRepository.findAllByUserIdAndStatus(userId ,status, PageRequest.of(page, size));
    }

    @Override
    public Page<WhitelistRequest> getAllByStatus(WhitelistRequestStatus status, Integer page, Integer size){
        return whitelistRequestRepository.findAllByStatus(status, PageRequest.of(page, size));
    }

    @Override
    public Page<WhitelistRequest> getAll(Integer page, Integer size){
        return whitelistRequestRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public boolean existsByCharacterFirstNameAndCharacterLastName(String characterFirstName, String characterLastName){
        return whitelistRequestRepository.existsByCharacterFirstNameAndCharacterLastName(characterFirstName, characterLastName);
    }

    @Override
    public boolean canAuthenticatedUserCreateRequest(){
        return authenticationService.getAuthenticatedUser()
                .map(user -> whitelistRequestRepository.countByUserId(user.getId()) < 5)
                .orElse(false);
    }
}

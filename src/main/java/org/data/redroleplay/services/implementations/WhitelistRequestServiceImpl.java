package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whitelistrequest.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.whitelistrequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.data.redroleplay.error_handling.costums.RecordNotFoundException;
import org.data.redroleplay.error_handling.costums.RedirectException;
import org.data.redroleplay.error_handling.costums.UserNeedAuthentication;
import org.data.redroleplay.error_handling.costums.ValidationException;
import org.data.redroleplay.repositories.website.WhitelistRequestRepository;

import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.CharacterService;
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

    private final CharacterService characterService;
    private final ModelMapper modelMapper = new ModelMapper();

    private final UserService userService;

    @Override
    public WhitelistRequest save(WhitelistRequestDto requestDto) {

        WhitelistRequest whitelistRequest = modelMapper.map(requestDto, WhitelistRequest.class);

        whitelistRequest.setRequestDate(LocalDateTime.now());
        whitelistRequest.setStatus(WhitelistRequestStatus.PENDING);

        User authenticatedUser = authenticationService.getAuthenticatedUser()
                .orElseThrow(() -> new UserNeedAuthentication("You need to be authenticated to create a whitelist request"));

        whitelistRequest.setUser(authenticatedUser);

        if(whitelistRequestRepository.countByUserId(authenticatedUser.getId()) >= authenticatedUser.getMaxWhitelistRequests()){
            throw new ValidationException(String.format("You have reached the maximum number of whitelist requests (%d)", authenticatedUser.getMaxWhitelistRequests()));
        }

        return whitelistRequestRepository.save(whitelistRequest);
    }

    @Override
    public WhitelistRequest verify(VerifyWhitelistRequestDto verifyWhitelistRequestDto , Long id) {

        WhitelistRequest fetchedWhitelistRequest = whitelistRequestRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        if(fetchedWhitelistRequest.getStatus() != WhitelistRequestStatus.PENDING){
            throw new RedirectException(String.format("/admin/whitelist/request/details/%d?error=A white lister has already verified this request", id));
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

        WhitelistRequest verifiedWhiteListRequest = whitelistRequestRepository.save(fetchedWhitelistRequest);

        if(WhitelistRequestStatus.ACCEPTED.equals(verifiedWhiteListRequest.getStatus())){
            characterService.create(verifiedWhiteListRequest);
        }

        return verifiedWhiteListRequest;
    }

    @Override
    public Optional<WhitelistRequest> getById(Long id){
        return whitelistRequestRepository.findById(id);
    }

    @Override
    public Page<WhitelistRequest> getAllByUserId(Long userId, Integer page, Integer size){
        if(Boolean.FALSE.equals(userService.existsById(userId))) throw new RecordNotFoundException("User not found");
        return whitelistRequestRepository.findAllByUserId(userId, PageRequest.of(page, size));
    }

    @Override
    public Page<WhitelistRequest> getAllByUserIdAndStatus(Long userId, WhitelistRequestStatus status, Integer page, Integer size){
        if(Boolean.FALSE.equals(userService.existsById(userId))) throw new RecordNotFoundException("User not found");
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
    public Page<WhitelistRequest> getAllById(Long id , Integer page, Integer size){
        return whitelistRequestRepository.findAllById(id, PageRequest.of(page, size));
    }

    @Override
    public Page<WhitelistRequest> getAllByCharacterFullName(String characterFullName , Integer page, Integer size){
        return whitelistRequestRepository.findAllByCharacterFullName(characterFullName, PageRequest.of(page, size));
    }
    @Override
    public Page<WhitelistRequest> getAllByUserFullName(String userFullName , Integer page, Integer size){
        return whitelistRequestRepository.findAllByUserFullName(userFullName, PageRequest.of(page, size));
    }
    @Override
    public boolean existsByCharacterFirstNameAndCharacterLastName(String characterFirstName, String characterLastName){
        return whitelistRequestRepository.existsByCharacterFirstNameAndCharacterLastName(characterFirstName, characterLastName);
    }

    @Override
    public boolean canAuthenticatedUserCreateRequest(){
        return authenticationService.getAuthenticatedUser()
                .map(user -> whitelistRequestRepository.countByUserId(user.getId()) < user.getMaxWhitelistRequests())
                .orElse(false);
    }
}

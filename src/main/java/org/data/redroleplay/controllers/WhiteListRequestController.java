package org.data.redroleplay.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDisplayForUserDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.WhitelistRequestService;
import org.data.redroleplay.validators.WhiteListRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/whitelist")
@RequiredArgsConstructor
@Scope("request")
public class WhiteListRequestController {

    private final WhitelistRequestService whitelistRequestService;

    private final AuthenticationService authenticationService;

    private final WhitelistRequestDto whitelistRequestDto = new WhitelistRequestDto();

    private final WhiteListRequestValidator whiteListRequestValidator;

    private final ModelMapper modelMapper = new ModelMapper();

    @ModelAttribute("whitelistDemand")
    public WhitelistRequestDto whitelistRequestDto() {
        return whitelistRequestDto;
    }

    @GetMapping
    public String showWhitelistPage(Model model) {

        User authenticatedUser = authenticationService.getAuthenticatedUser()
                .orElseThrow(() -> new UserNeedAuthentication("User not authenticated"));

        CustomPageResponse<WhitelistRequest, WhitelistRequestDisplayForUserDto> whitelistRequests =
                whitelistRequestService.getAllByUserId(authenticatedUser.getId(), 0, 5);

        model.addAttribute("whitelistRequests", whitelistRequests);

        return "pages/whiteList/whitelist";
    }

    @GetMapping("/request")
    public String showWhitelistRequestPage(Model model) {

        model.addAttribute("whitelistRequest", whitelistRequestDto);

        return "pages/whiteList/whitelist-request";
    }

    @GetMapping("/request/details/{id}")
    public String showWhitelistRequestDetailsPage(@PathVariable Long id , Model model) {

        WhitelistRequest whitelistRequest = whitelistRequestService.getById(id)
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        WhitelistRequestDisplayForUserDto whitelistRequestDisplayForUserDto =
                modelMapper.map(whitelistRequest, WhitelistRequestDisplayForUserDto.class);

        model.addAttribute("whitelistRequestDetails", whitelistRequestDisplayForUserDto);

        return "pages/whiteList/whitelist-request-details";
    }

    @PostMapping("/request")
    public String createWhitelistRequest(
            @Valid @ModelAttribute("whitelistDemand") WhitelistRequestDto whitelistRequestDto,
            BindingResult result) {

        whiteListRequestValidator.validate(whitelistRequestDto, result);

        if (result.hasErrors()) return "pages/whiteList/whitelist-request";

        whitelistRequestService.save(whitelistRequestDto);

        return "redirect:/whitelist?successRequest";
    }

}
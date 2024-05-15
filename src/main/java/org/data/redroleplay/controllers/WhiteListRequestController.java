package org.data.redroleplay.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDisplayForUserDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.WhitelistRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/whitelist")
@RequiredArgsConstructor
public class WhiteListRequestController {

    private final WhitelistRequestService whitelistRequestService;

    private final AuthenticationService authenticationService;

    private final WhitelistRequestDto whitelistRequestDto = new WhitelistRequestDto();

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

    @PostMapping("/request")
    public String createWhitelistRequest(
            @Valid @ModelAttribute("whitelistDemand") WhitelistRequestDto whitelistRequestDto,
            BindingResult result) {

        if (result.hasErrors()) return "pages/whiteList/whitelist-request";

        whitelistRequestService.save(whitelistRequestDto);

        return "redirect:/whitelist?successRequest";
    }

}

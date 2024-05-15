package org.data.redroleplay.controllers;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDisplayForUserDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.WhitelistRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/whitelist")
@RequiredArgsConstructor
public class WhiteListRequestController {

    private final WhitelistRequestService whitelistRequestService;

    private final AuthenticationService authenticationService;

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
    public String showWhitelistRequestPage() {
        return "pages/whiteList/whitelist-request";
    }

}

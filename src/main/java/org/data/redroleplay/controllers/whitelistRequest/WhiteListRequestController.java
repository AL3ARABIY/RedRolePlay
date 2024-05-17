package org.data.redroleplay.controllers.whitelistRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDisplayForUserDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthorisation;
import org.data.redroleplay.mappers.WhitelistRequestDisplayForUserDtoMapper;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.AuthenticationService;
import org.data.redroleplay.services.WhitelistRequestService;
import org.data.redroleplay.validators.WhiteListRequestValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/whitelist")
@RequiredArgsConstructor
@Scope("request")
public class WhiteListRequestController {

    private final WhitelistRequestService whitelistRequestService;

    private final AuthenticationService authenticationService;

    private final WhitelistRequestDto whitelistRequestDto = new WhitelistRequestDto();

    private final WhiteListRequestValidator whiteListRequestValidator;

    private final WhitelistRequestDisplayForUserDtoMapper mapper;

    @ModelAttribute("whitelistDemand")
    public WhitelistRequestDto whitelistRequestDto() {
        return whitelistRequestDto;
    }

    @GetMapping
    public String showUserWhitelistRequestsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        User authenticatedUser = authenticationService.getAuthenticatedUser()
                .orElseThrow(() -> new UserNeedAuthentication("User not authenticated"));

        Page<WhitelistRequest> whitelistRequests = whitelistRequestService.getAllByUserId(authenticatedUser.getId(), page, size);

        CustomPageResponse<WhitelistRequest, WhitelistRequestDisplayForUserDto> costumePage =
                new CustomPageResponse<>(whitelistRequests, WhitelistRequestDisplayForUserDto.class);

        model.addAttribute("costumePage", costumePage );

        return "pages/whiteList/whitelist";
    }

    @GetMapping("/request")
    public String showUserWhitelistRequestPage(Model model) {

        model.addAttribute("whitelistRequest", whitelistRequestDto);

        return "pages/whiteList/whitelist-request";
    }

    @GetMapping("/request/details/{id}")
    public String showUserWhitelistRequestDetailsPage(@PathVariable Long id , Model model) {

        WhitelistRequest whitelistRequest = whitelistRequestService.getById(id)
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        User authenticatedUser = authenticationService.getAuthenticatedUser()
                .orElseThrow(() -> new UserNeedAuthentication("User not authenticated"));

        // Check if the user is the owner of the whitelist request
        if (!whitelistRequest.getUser().getId().equals(authenticatedUser.getId())) {
            throw new UserNeedAuthorisation("User not authorised to view this page");
        }

        WhitelistRequestDisplayForUserDto whitelistRequestDisplayForUserDto = mapper.map(whitelistRequest);

        model.addAttribute("userWhitelistRequestDetails", whitelistRequestDisplayForUserDto);

        return "pages/whiteList/whitelist-request-details";
    }

    @PostMapping("/request")
    public String createUserWhitelistRequest(
            @Valid @ModelAttribute("whitelistDemand") WhitelistRequestDto whitelistRequestDto,
            BindingResult result) {

        whiteListRequestValidator.validate(whitelistRequestDto, result);

        if (result.hasErrors()) return "pages/whiteList/whitelist-request";

        whitelistRequestService.save(whitelistRequestDto);

        return "redirect:/whitelist?successRequest";
    }

}

package org.data.redroleplay.controllers.whitelistRequest;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDisplayForAdminDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.mappers.WhitelistRequestDisplayForAdminDtoMapper;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.WhitelistRequestService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/whitelist")
@RequiredArgsConstructor
@Scope("request")
public class WhiteListRequestAdminController {

    private final WhitelistRequestService whitelistRequestService;

    private final WhitelistRequestDisplayForAdminDtoMapper mapper;

    @GetMapping
    public String showWhitelistRequestsPage(Model model) {

        Page<WhitelistRequest> whitelistRequests = whitelistRequestService.getAll(0, 5);

        CustomPageResponse<WhitelistRequest, WhitelistRequestDisplayForAdminDto> allWhitelistRequests =
                new CustomPageResponse<>(whitelistRequests, WhitelistRequestDisplayForAdminDto.class , mapper.getModelMapper());

        model.addAttribute("allWhitelistRequests", allWhitelistRequests);

        return "pages/whiteList/admin/whitelist";
    }

    @GetMapping("/request/details/{id}")
    public String showWhitelistRequestDetailsPage(@PathVariable Long id , Model model) {

        WhitelistRequest whitelistRequest = whitelistRequestService.getById(id)
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        WhitelistRequestDisplayForAdminDto whitelistRequestDisplayForAdminDto =
                mapper.map(whitelistRequest);

        model.addAttribute("whitelistRequestDetails", whitelistRequestDisplayForAdminDto);

        return "pages/whiteList/admin/whitelist-request-details";
    }


}

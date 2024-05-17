package org.data.redroleplay.controllers.whitelistRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whiteListRequest.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.whiteListRequest.WhitelistRequestDisplayForAdminDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.data.redroleplay.errorHandling.costums.RecordNotFoundException;
import org.data.redroleplay.errorHandling.costums.ValidationException;
import org.data.redroleplay.mappers.WhitelistRequestDisplayForAdminDtoMapper;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.WhitelistRequestService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/whitelist")
@RequiredArgsConstructor
@Scope("request")
public class WhiteListRequestAdminController {

    private final WhitelistRequestService whitelistRequestService;

    private final WhitelistRequestDisplayForAdminDtoMapper mapper;

    private final VerifyWhitelistRequestDto verifyWhitelistRequestDto = new VerifyWhitelistRequestDto();

    @ModelAttribute("verifyWhitelistRequest")
    public VerifyWhitelistRequestDto verifyWhitelistRequestDto() {
        return verifyWhitelistRequestDto;
    }

    @GetMapping
    public String showWhitelistRequestsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<WhitelistRequest> whitelistRequests = whitelistRequestService.getAll(page, size);

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

    @GetMapping("/request/verify/{id}")
    public String verifyWhitelistRequest(@PathVariable Long id, Model model) {

        WhitelistRequest whitelistRequest = whitelistRequestService.getById(id)
                .orElseThrow(() -> new RecordNotFoundException("Whitelist request not found"));

        if(whitelistRequest.getStatus() != WhitelistRequestStatus.PENDING){
            throw new ValidationException("Whitelist request already verified");
        }

        model.addAttribute("verifyWhitelistRequest", verifyWhitelistRequestDto);
        model.addAttribute("whitelistRequestId", id);

        return "pages/whiteList/admin/whitelist-request-verify";
    }

    @PostMapping("/request/verify/{id}")
    public String verifyWhitelistRequest(
            @Valid @ModelAttribute("verifyWhitelistRequest") VerifyWhitelistRequestDto requestDto,
            BindingResult result,
            @PathVariable Long id,
            Model model) {

        if(result.hasErrors()){
            model.addAttribute("whitelistRequestId", id);
            return "pages/whiteList/admin/whitelist-request-verify";
        }

        whitelistRequestService.verify(requestDto , id);

        return String.format("redirect:/admin/whitelist/request/details/%d", id);
    }


}

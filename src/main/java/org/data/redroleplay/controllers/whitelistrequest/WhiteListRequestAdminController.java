package org.data.redroleplay.controllers.whitelistrequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.whitelistrequest.VerifyWhitelistRequestDto;
import org.data.redroleplay.dtos.whitelistrequest.WhiteListRequestSearchByDto;
import org.data.redroleplay.dtos.whitelistrequest.WhitelistRequestDisplayForAdminDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.SearchFieldsWhiteListRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.data.redroleplay.error_handling.costums.RecordNotFoundException;
import org.data.redroleplay.mappers.WhitelistRequestDisplayForAdminDtoMapper;
import org.data.redroleplay.models.CustomPageResponse;
import org.data.redroleplay.services.WhitelistRequestService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.BiFunction;

@Controller
@RequestMapping("/admin/whitelist")
@RequiredArgsConstructor
@Scope("request")
public class WhiteListRequestAdminController {

    private final WhitelistRequestService whitelistRequestService;

    private final WhitelistRequestDisplayForAdminDtoMapper mapper;

    private final VerifyWhitelistRequestDto verifyWhitelistRequestDto = new VerifyWhitelistRequestDto();
    private final WhiteListRequestSearchByDto whiteListRequestSearchByDto = new WhiteListRequestSearchByDto();

    @ModelAttribute("verifyWhitelistRequest")
    public VerifyWhitelistRequestDto verifyWhitelistRequestDto() {
        return verifyWhitelistRequestDto;
    }

    @ModelAttribute("whiteListRequestSearchByDto")
    public WhiteListRequestSearchByDto whiteListRequestSearchByDto() {
        return whiteListRequestSearchByDto;
    }

    @GetMapping
    public String showWhitelistRequestsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) WhitelistRequestStatus status,
            Model model) {

        Page<WhitelistRequest> whitelistRequests;

        if(status != null)  whitelistRequests = whitelistRequestService.getAllByStatus(status, page, size);
        else whitelistRequests = whitelistRequestService.getAll(page, size);

        CustomPageResponse<WhitelistRequest, WhitelistRequestDisplayForAdminDto> allWhitelistRequests =
                new CustomPageResponse<>(whitelistRequests, WhitelistRequestDisplayForAdminDto.class , mapper.getModelMapper());

        model.addAttribute("allWhitelistRequests", allWhitelistRequests);
        model.addAttribute("searchByDto", whiteListRequestSearchByDto);

        if(status != null) model.addAttribute("status", status.toString());

        return "pages/whiteList/admin/index";
    }

    @GetMapping("/search")
    public String showWhitelistRequestsResult(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @Valid @ModelAttribute("whiteListRequestSearchByDto") WhiteListRequestSearchByDto searchByDto,
            Model model) {

        if(SearchFieldsWhiteListRequest.ID.equals(searchByDto.getSearchField()) && !searchByDto.getSearchValue().matches("\\d+")){
            return "redirect:/admin/whitelist?error=Invalid search value for ID field";
        }

        Page<WhitelistRequest> searchResult = searchMenu().get(searchByDto.getSearchField())
                .apply(searchByDto.getSearchValue(), new int[]{page, size});

        CustomPageResponse<WhitelistRequest, WhitelistRequestDisplayForAdminDto> whitelistRequestSearchResult =
                new CustomPageResponse<>(searchResult, WhitelistRequestDisplayForAdminDto.class , mapper.getModelMapper());

        model.addAttribute("whitelistRequestSearchResult", whitelistRequestSearchResult);
        model.addAttribute("searchByDto", searchByDto);

        return "pages/whiteList/admin/search-result";
    }

    private Map<SearchFieldsWhiteListRequest , BiFunction<String,int[],Page<WhitelistRequest>>> searchMenu(){
        return Map.of(
                SearchFieldsWhiteListRequest.ID , (value, pageableInfo) -> whitelistRequestService.getAllById(Long.parseLong(value), pageableInfo[0], pageableInfo[1]),
                SearchFieldsWhiteListRequest.USER_FULL_NAME , (value, pageableInfo) -> whitelistRequestService.getAllByUserFullName(value, pageableInfo[0], pageableInfo[1]),
                SearchFieldsWhiteListRequest.CHARACTER_FULL_NAME , (value, pageableInfo) -> whitelistRequestService.getAllByCharacterFullName(value, pageableInfo[0], pageableInfo[1])
        );
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
            return String.format("redirect:/admin/whitelist/request/details/%d?alreadyVerified", id);
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

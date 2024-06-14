package org.data.redroleplay.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.user.UpdateUserMtaSerialRequestDto;
import org.data.redroleplay.dtos.user.UpdateUserResetPasswordRequestDto;
import org.data.redroleplay.dtos.user.UserDetailsDisplayForAdminDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.error_handling.costums.RecordNotFoundException;
import org.data.redroleplay.services.UserService;
import org.data.redroleplay.validators.MtaSerialValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Scope("session")
public class UserAdminController {

    private final UserService userService;

    private final MtaSerialValidator mtaSerialValidator;
    private final UpdateUserMtaSerialRequestDto updateUserMtaSerialRequest = new UpdateUserMtaSerialRequestDto();

    private final UpdateUserResetPasswordRequestDto updateUserResetPasswordRequest = new UpdateUserResetPasswordRequestDto();

    private final ModelMapper modelMapper = new ModelMapper();

    @ModelAttribute("updateUserMtaSerialRequest")
    public UpdateUserMtaSerialRequestDto updateUserMtaSerialRequest() {
        return updateUserMtaSerialRequest;
    }

    @ModelAttribute("updateUserResetPasswordRequest")
    public UpdateUserResetPasswordRequestDto updateUserResetPasswordRequest() {
        return updateUserResetPasswordRequest;
    }

    @GetMapping("/details/{id}")
    public String showUserDetails(
            @PathVariable("id") Long id,
            Model model
    ) {

        User user = userService.getUserById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User with id %d not found", id)));

        UserDetailsDisplayForAdminDto userDetailsDisplayForAdminDto = modelMapper.map(user, UserDetailsDisplayForAdminDto.class);

        model.addAttribute("user", userDetailsDisplayForAdminDto);

        return "pages/user/admin/user-details";
    }

    @GetMapping("/reset-password/{id}")
    public String resetPassword(
            @PathVariable("id") Long id,
            Model model
    ) {

        User user = userService.getUserById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User with id %d not found", id)));

        updateUserResetPasswordRequest.setUserId(id);
        updateUserResetPasswordRequest.setNewPassword(user.getMtaUsername() + "123");
        model.addAttribute("updateUserResetPasswordRequest", updateUserResetPasswordRequest);

        return "pages/user/admin/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid @ModelAttribute("updateUserResetPasswordRequest") UpdateUserResetPasswordRequestDto updateUserResetPasswordRequest,
            BindingResult result,
            Model model
    ) {

        if(result.hasErrors()) {
            model.addAttribute("updateUserResetPasswordRequest", updateUserResetPasswordRequest);
            return "pages/user/admin/reset-password";
        }

        userService.updateUserPassword(updateUserResetPasswordRequest);

        return "redirect:/admin/user/details/" + updateUserResetPasswordRequest.getUserId() + "?success=Password reseted successfully";
    }

    @GetMapping("/change-serial/{id}")
    public String changeSerial(
            @PathVariable("id") Long id,
            Model model
    ) {

        User user = userService.getUserById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User with id %d not found", id)));

        updateUserMtaSerialRequest.setUserId(id);
        updateUserMtaSerialRequest.setMtaSerial(user.getMtaSerial());
        model.addAttribute("updateUserMtaSerialRequest", updateUserMtaSerialRequest);

        return "pages/user/admin/change-serial";
    }

    @PostMapping("/change-serial")
    public String changeSerial(
            @Valid @ModelAttribute("updateUserMtaSerialRequest") UpdateUserMtaSerialRequestDto updateUserMtaSerialRequest,
            BindingResult result,
            Model model
    ) {
        mtaSerialValidator.validate(updateUserMtaSerialRequest.getMtaSerial(), result);

        if(result.hasErrors()) {
            model.addAttribute("updateUserMtaSerialRequest", updateUserMtaSerialRequest);
            return "pages/user/admin/change-serial";
        }

        userService.updateUserMtaSerial(updateUserMtaSerialRequest);

        return "redirect:/admin/user/details/" + updateUserMtaSerialRequest.getUserId() + "?success=Serial updated successfully";
    }

}

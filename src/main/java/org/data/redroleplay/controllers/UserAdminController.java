package org.data.redroleplay.controllers;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.user.UserDetailsDisplayForAdminDto;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.error_handling.costums.RecordNotFoundException;
import org.data.redroleplay.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Scope("session")
public class UserAdminController {

    private final UserService userService;

    private final ModelMapper modelMapper = new ModelMapper();

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
}

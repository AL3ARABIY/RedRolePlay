package org.data.redroleplay.services;

import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.entities.User;

import java.util.Optional;

public interface UserService{
    User save(UserRegistrationDto registrationDto);

    Optional<User> getUserByUsername(String username);
}

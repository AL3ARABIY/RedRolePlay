package org.data.redroleplay.services;

import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.entities.website.User;

import java.util.Optional;

public interface UserService{
    User save(UserRegistrationDto registrationDto);

    User updateUserInfoOnLogin(String ipAddress);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserById(Long id);

    Boolean existsByEmail(String email);

    Boolean existsByMtaUsername(String mtUsername);

    Boolean existsByDiscordId(String discordId);

    Boolean existsByMtaSerial(String mtaSerial);

    Boolean existsByDiscordUsername(String discordUsername);

    Boolean existsById(Long id);
}

package org.data.redroleplay.services;

import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.dtos.user.UpdateUserMtaSerialRequestDto;
import org.data.redroleplay.dtos.user.UpdateUserResetPasswordRequestDto;
import org.data.redroleplay.entities.website.User;

import java.util.Optional;

public interface UserService{
    User save(UserRegistrationDto registrationDto);

    User updateUserInfoOnLogin(String ipAddress);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserById(Long id);

    void updateUserMtaSerial(UpdateUserMtaSerialRequestDto request);

    void updateUserPassword(UpdateUserResetPasswordRequestDto request);

    boolean existsByEmail(String email);

    boolean existsByMtaUsername(String mtUsername);

    boolean existsByDiscordId(String discordId);

    boolean existsByMtaSerial(String mtaSerial);

    boolean existsByDiscordUsername(String discordUsername);

    boolean existsById(Long id);
}

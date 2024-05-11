package org.data.redroleplay.services.implementations;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.entities.website.Role;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.repositories.UserRepository;
import org.data.redroleplay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(UserRegistrationDto registrationDto) {

        User user = User.builder()
                        .email(registrationDto.getEmail())
                        .mtaUsername(registrationDto.getMtaUsername())
                        .firstName(registrationDto.getFirstName())
                        .lastName(registrationDto.getLastName())
                        .birthDate(registrationDto.getBirthDate())
                        .password(new BCryptPasswordEncoder().encode(registrationDto.getPassword()))
                        .roles(List.of(new Role("USER")))
                .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByMtaUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByMtaUsername(String mtUsername){
        return userRepository.existsByMtaUsername(mtUsername);
    }

    @Override
    public Boolean existsByDiscordId(String discordId){
        return userRepository.existsByDiscordId(discordId);
    }

    @Override
    public Boolean existsByMtaSerial(String mtaSerial){
        return userRepository.existsByMtaSerial(mtaSerial);
    }

}

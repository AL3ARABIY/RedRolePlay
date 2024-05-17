package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.dtos.UserRegistrationDto;
import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.entities.website.Authority;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.enums.BaseAuthority;
import org.data.redroleplay.repositories.website.UserRepository;
import org.data.redroleplay.services.AccountService;
import org.data.redroleplay.services.PasswordHasher;
import org.data.redroleplay.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordHasher passwordHasher;

    private final AccountService accountService;

    @Override
    @Transactional
    public User save(UserRegistrationDto registrationDto) {

        String salt = passwordHasher.generateSalt();
        String hashedPassword = passwordHasher.generateHashedPassword(
                registrationDto.getPassword(), salt
        );

        User user = User.builder()
                        .email(registrationDto.getEmail())
                        .mtaUsername(registrationDto.getMtaUsername())
                        .mtaSerial(registrationDto.getMtaSerial())
                        .discordId(registrationDto.getDiscordId())
                        .discordUsername(registrationDto.getDiscordUsername())
                        .discordAvatar(registrationDto.getDiscordAvatar())
                        .firstName(registrationDto.getFirstName())
                        .lastName(registrationDto.getLastName())
                        .birthDate(registrationDto.getBirthDate())
                        .password(new BCryptPasswordEncoder().encode(registrationDto.getPassword()))
                        .mtaPassword(hashedPassword)
                        .registerDate(LocalDateTime.now())
                        .salt(salt)
                        .authorities(List.of(new Authority(BaseAuthority.SIMPLE_ACCESS)))
                .build();

        Account savedAccount = accountService.save(user);

        user.setAccountId(savedAccount.getId());

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByMtaUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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

    @Override
    public Boolean existsByDiscordUsername(String discordUsername){
        return  userRepository.existsByDiscordUsername(discordUsername);
    }

    @Override
    public Boolean existsById(Long id){
        return userRepository.existsById(id);
    }
}

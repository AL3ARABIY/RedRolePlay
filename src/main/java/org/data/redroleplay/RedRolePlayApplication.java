package org.data.redroleplay;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.services.AccountService;
import org.data.redroleplay.services.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class RedRolePlayApplication {

    private final UserService userService;

    private final AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(RedRolePlayApplication.class, args);
    }

    @Bean
    @Transactional
    ApplicationRunner applicationRunner() {
        return args -> {
            User user = userService.getUserByUsername("AL3ARABIY").orElseThrow(
                    () -> new RuntimeException("User not found")
            );

            Account account = Account.builder()
                    .email(user.getEmail())
                    .username(user.getMtaUsername())
                    .password(user.getMtaPassword())
                    .salt(user.getSalt())
                    .discordId(user.getDiscordId())
                    .mtaSerial(user.getMtaSerial())
                    .build();

            Account accountSaved = accountService.save(account);

            System.out.println("Account saved: " + accountSaved);
        };
    }
}

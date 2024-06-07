package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.repositories.game.AccountRepository;
import org.data.redroleplay.services.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account save(User user) {

        Account account = Account.builder()
                .email(user.getEmail())
                .username(user.getMtaUsername())
                .password(user.getMtaPassword())
                .salt(user.getSalt())
                .discordId(user.getDiscordId())
                .discordUsername(user.getDiscordUsername())
                .mtaSerial(user.getMtaSerial())
                .registerDate(user.getRegisterDate())
                .build();

        return accountRepository.save(account);
    }

}

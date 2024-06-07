package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.error_handling.costums.RecordNotFoundException;
import org.data.redroleplay.repositories.game.AccountRepository;
import org.data.redroleplay.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void updateUserMtaSerial(String mtaSerial, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Account with id %d not found", accountId)));
        account.setMtaSerial(mtaSerial);
        accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

}

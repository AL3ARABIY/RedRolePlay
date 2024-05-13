package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.repositories.game.AccountRepository;
import org.data.redroleplay.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account save(User user) {

        Optional<Account> lastAccount = accountRepository.findFirstByOrderByIdDesc();

        Long id = lastAccount.map(Account::getId).orElse(1L) + 1;

        Account account = Account.builder()
                .id(id)
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
    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> getByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

}

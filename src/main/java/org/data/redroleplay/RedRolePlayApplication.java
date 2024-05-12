package org.data.redroleplay;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.game.Account;
import org.data.redroleplay.repositories.game.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@RequiredArgsConstructor
public class RedRolePlayApplication {

    private final AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(RedRolePlayApplication.class, args);
    }

//    @Bean
//    ApplicationRunner applicationRunner() {
//        return args -> {
//            Account account = Account.builder()
//                    .id(1L)
//                    .email("test@gmail.com")
//                    .username("test")
//                    .password("test")
//                    .salt("test")
//                    .discordId("test")
//                    .mtaSerial("test")
//                    .build();
//
//            Account savedAccount = accountRepository.save(account);
//
//            System.out.println(savedAccount);
//        };
//    }
}


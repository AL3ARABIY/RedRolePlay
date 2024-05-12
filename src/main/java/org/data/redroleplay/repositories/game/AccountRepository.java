package org.data.redroleplay.repositories.game;

import org.data.redroleplay.entities.game.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findFirstByOrderByIdDesc();
    Optional<Account> findByUsername(String username);

}
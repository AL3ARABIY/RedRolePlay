package org.data.redroleplay.repositories.game;

import org.data.redroleplay.entities.game.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}

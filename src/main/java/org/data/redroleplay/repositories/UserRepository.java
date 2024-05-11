package org.data.redroleplay.repositories;

import org.data.redroleplay.entities.website.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByMtaUsername(String mtUsername);
}

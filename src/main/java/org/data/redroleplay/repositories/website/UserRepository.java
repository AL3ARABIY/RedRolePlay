package org.data.redroleplay.repositories.website;

import org.data.redroleplay.entities.website.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByMtaUsername(String mtUsername);

    Boolean existsByEmail(String email);

    Boolean existsByMtaUsername(String mtUsername);

    Boolean existsByDiscordId(String discordId);

    Boolean existsByMtaSerial(String mtaSerial);

    Boolean existsByDiscordUsername(String discordUsername);
}

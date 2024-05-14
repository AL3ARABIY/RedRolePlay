package org.data.redroleplay.repositories.website;

import org.data.redroleplay.entities.website.WhitelistRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhitelistRequestRepository extends JpaRepository<WhitelistRequest, Long>{ }

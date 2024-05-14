package org.data.redroleplay.repositories.website;

import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhitelistRequestRepository extends JpaRepository<WhitelistRequest, Long>{
    Page<WhitelistRequest> findAllByUserId(Long userId , Pageable pageable);

    Page<WhitelistRequest> findAllByStatus(WhitelistRequestStatus status , Pageable pageable);
}

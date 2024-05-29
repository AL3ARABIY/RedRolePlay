package org.data.redroleplay.repositories.website;

import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WhitelistRequestRepository extends JpaRepository<WhitelistRequest, Long>{

    Page<WhitelistRequest> findAllByUserId(Long userId , Pageable pageable);

    Page<WhitelistRequest> findAllByStatus(WhitelistRequestStatus status , Pageable pageable);

    Page<WhitelistRequest> findAllByUserIdAndStatus(Long userId , WhitelistRequestStatus status , Pageable pageable);

    boolean existsByCharacterFirstNameAndCharacterLastName(String characterFirstName, String characterLastName);

    @Query("SELECT COUNT(wr) FROM WhitelistRequest wr WHERE wr.user.id = :userId")
    Long countByUserId(Long userId);
}

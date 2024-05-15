package org.data.redroleplay.repositories.website;

import org.data.redroleplay.entities.website.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {}

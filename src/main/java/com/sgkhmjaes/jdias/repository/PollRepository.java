package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Poll;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Poll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PollRepository extends JpaRepository<Poll,Long> {

}

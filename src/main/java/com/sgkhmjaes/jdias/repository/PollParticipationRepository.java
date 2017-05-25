package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.PollParticipation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PollParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PollParticipationRepository extends JpaRepository<PollParticipation,Long> {

}

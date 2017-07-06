package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.EventParticipation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EventParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventParticipationRepository extends JpaRepository<EventParticipation,Long> {
    
}

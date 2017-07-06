package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Participation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Participation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    
}

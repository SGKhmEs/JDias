package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Reshare;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Reshare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReshareRepository extends JpaRepository<Reshare, Long> {

}

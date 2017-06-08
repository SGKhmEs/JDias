package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Retraction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Retraction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetractionRepository extends JpaRepository<Retraction,Long> {

}

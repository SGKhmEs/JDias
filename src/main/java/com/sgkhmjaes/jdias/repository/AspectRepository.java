package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Aspect;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Aspect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectRepository extends JpaRepository<Aspect,Long> {
    
}

package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.AspectVisiblity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AspectVisiblity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectVisiblityRepository extends JpaRepository<AspectVisiblity,Long> {
    
}

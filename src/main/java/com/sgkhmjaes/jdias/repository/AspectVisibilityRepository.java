package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.AspectVisibility;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AspectVisibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectVisibilityRepository extends JpaRepository<AspectVisibility,Long> {

}

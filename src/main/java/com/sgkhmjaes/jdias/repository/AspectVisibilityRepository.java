package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Aspectvisibility;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Aspectvisibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectvisibilityRepository extends JpaRepository<Aspectvisibility,Long> {

}

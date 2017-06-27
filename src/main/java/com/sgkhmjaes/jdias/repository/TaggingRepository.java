package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Tagging;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tagging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaggingRepository extends JpaRepository<Tagging,Long> {

}

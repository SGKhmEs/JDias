package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.HashTag;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HashTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {
    
}

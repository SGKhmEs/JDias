package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Photo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {

}

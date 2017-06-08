package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Like;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

}

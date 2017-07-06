package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.TagFollowing;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TagFollowing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagFollowingRepository extends JpaRepository<TagFollowing,Long> {
    
}

package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Like;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("FROM Like where post_id =?1")
    public List<Like> findaAllByPostId(Long id);
}
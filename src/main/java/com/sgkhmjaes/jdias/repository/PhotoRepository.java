package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Photo;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("FROM Photo where status_message_id =?1")
    public List<Photo> findAllByPostId(Long id);
}

package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Comment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    
}

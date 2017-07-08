package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Conversation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Conversation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    
}

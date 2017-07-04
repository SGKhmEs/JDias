package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Conversation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Conversation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversationRepository extends JpaRepository<Conversation,Long> {

    @Query("select distinct conversation from Conversation conversation left join fetch conversation.participants")
    List<Conversation> findAllWithEagerRelationships();

    @Query("select conversation from Conversation conversation left join fetch conversation.participants where conversation.id =:id")
    Conversation findOneWithEagerRelationships(@Param("id") Long id);
    
}

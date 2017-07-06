package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.StatusMessage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StatusMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusMessageRepository extends JpaRepository<StatusMessage,Long> {
    
}

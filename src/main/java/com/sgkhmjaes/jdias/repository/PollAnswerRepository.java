package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.PollAnswer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the PollAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PollAnswerRepository extends JpaRepository<PollAnswer, Long> {

}

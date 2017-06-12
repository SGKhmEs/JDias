package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.AspectMembership;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the AspectMembership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectMembershipRepository extends JpaRepository<AspectMembership, Long> {

}

package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.AccountDeletion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AccountDeletion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountDeletionRepository extends JpaRepository<AccountDeletion,Long> {

}

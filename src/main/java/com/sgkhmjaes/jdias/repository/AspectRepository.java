package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.domain.User;
import com.sgkhmjaes.jdias.domain.UserAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Aspect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectRepository extends JpaRepository<Aspect,Long> {
    List<Aspect> findAllByAspectMemberships(Long userId);
}

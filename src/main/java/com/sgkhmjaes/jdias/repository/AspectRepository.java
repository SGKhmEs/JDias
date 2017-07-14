package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.domain.Person;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the Aspect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AspectRepository extends JpaRepository<Aspect,Long> {
    List<Aspect> findAllByPerson(Person person);
}

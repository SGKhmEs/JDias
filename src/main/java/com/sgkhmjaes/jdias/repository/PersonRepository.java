package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Person;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    
    @Query("select distinct person from Person person left join fetch person.conversations")
    List<Person> findAllWithEagerRelationships();

    @Query("select person from Person person left join fetch person.conversations where person.id =:id")
    Person findOneWithEagerRelationships(@Param("id") Long id);
    
}

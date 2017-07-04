package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Person;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    
    @Query("SELECT person FROM Person person where person.diasporaId =:diasporaId") 
    Person findPersonByDiasporaId(@Param("diasporaId") String diasporaId);
    
}

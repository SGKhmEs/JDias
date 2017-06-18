package com.sgkhmjaes.jdias.repository;

import com.sgkhmjaes.jdias.domain.Contact;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

//    @Query("where owner_id = ?1")
//    public List<Contact> findByOwner(Long id);
}

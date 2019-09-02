package com.lumie.contact.repository;


import com.lumie.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c where lower(c.firstName) like lower(concat('%', :search, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :search, '%'))")
    List<Contact> findByFirstNameOrLastName(@Param("search") String search);
}
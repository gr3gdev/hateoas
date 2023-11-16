package com.example.hateoas.dao.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hateoas.dao.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.contacts from User u where u.id = :id")
    Collection<User> findContactsById(@Param("id") Long id);

}

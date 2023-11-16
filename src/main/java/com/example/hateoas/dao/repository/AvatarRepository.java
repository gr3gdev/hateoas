package com.example.hateoas.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hateoas.dao.bean.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    
}

package com.example.hateoas.domain.service;

import java.util.Optional;

import org.springframework.hateoas.CollectionModel;

import com.example.hateoas.domain.model.AbstractModel;

public interface IService<M extends AbstractModel> {

    CollectionModel<M> findAll();

    Optional<M> findById(Long id);

    M save(M model);

    void deleteById(Long id);

}

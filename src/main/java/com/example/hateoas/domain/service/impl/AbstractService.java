package com.example.hateoas.domain.service.impl;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;

import com.example.hateoas.domain.assembler.AbstractRepresentationModelAssembler;
import com.example.hateoas.domain.model.AbstractModel;
import com.example.hateoas.domain.service.IService;

public abstract class AbstractService<E, R extends JpaRepository<E, Long>, M extends AbstractModel, A extends AbstractRepresentationModelAssembler<E, M>>
        implements IService<M> {

    protected final R repository;
    protected final A representationModelAssembler;

    public AbstractService(R repository, A representationModelAssembler) {
        this.repository = repository;
        this.representationModelAssembler = representationModelAssembler;
    }

    @Override
    public CollectionModel<M> findAll() {
        return representationModelAssembler.toCollectionModel(repository.findAll());
    }

    @Override
    public Optional<M> findById(Long id) {
        return repository.findById(id)
                .map(representationModelAssembler::toModel);
    }

    @Override
    public M save(M model) {
        return representationModelAssembler.toModel(repository.save(representationModelAssembler.toEntity(model)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}

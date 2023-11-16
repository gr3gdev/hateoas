package com.example.hateoas.domain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.hateoas.domain.model.AbstractModel;
import com.example.hateoas.domain.service.IService;

public abstract class AbstractController<M extends AbstractModel, S extends IService<M>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    protected final S service;

    public AbstractController(S service) {
        this.service = service;
    }

    @GetMapping("/")
    public CollectionModel<M> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<M> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(m -> new ResponseEntity<M>(m, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<M> create(@RequestBody M model) {
        if (model.getId() != null) {
            LOGGER.warn("Create tentative with an id");
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<M>(
                service.save(model),
                HttpStatus.CREATED);
    }

    @PutMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<M> update(@RequestBody M model) {
        if (model.getId() == null) {
            LOGGER.warn("Update tentative with id null");
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<M>(
                service.save(model),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}

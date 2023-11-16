package com.example.hateoas.domain.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hateoas.domain.model.UserModel;
import com.example.hateoas.domain.service.impl.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController
        extends AbstractController<UserModel, UserService> {

    public UserController(UserService service) {
        super(service);
    }

    @GetMapping("/{id}/contacts")
    public CollectionModel<UserModel> contacts(@PathVariable Long id) {
        return service.findContactsById(id);
    }

}

package com.example.hateoas.domain.service.impl;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import com.example.hateoas.dao.bean.User;
import com.example.hateoas.dao.repository.UserRepository;
import com.example.hateoas.domain.assembler.UserRepresentationModelAssembler;
import com.example.hateoas.domain.model.UserModel;

@Service
public class UserService extends AbstractService<User, UserRepository, UserModel, UserRepresentationModelAssembler> {

    public UserService(UserRepository repository, UserRepresentationModelAssembler representationModelAssembler) {
        super(repository, representationModelAssembler);
    }

    public CollectionModel<UserModel> findContactsById(Long id) {
        return representationModelAssembler.toCollectionModel(repository.findContactsById(id));
    }

}

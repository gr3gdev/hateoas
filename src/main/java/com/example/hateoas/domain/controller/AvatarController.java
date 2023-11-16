package com.example.hateoas.domain.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hateoas.domain.model.AvatarModel;
import com.example.hateoas.domain.service.impl.AvatarService;

@RestController
@RequestMapping(value = "/avatars")
public class AvatarController
        extends AbstractController<AvatarModel, AvatarService> {

    public AvatarController(AvatarService service) {
        super(service);
    }

}

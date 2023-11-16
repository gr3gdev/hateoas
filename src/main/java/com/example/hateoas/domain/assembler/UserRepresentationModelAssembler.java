package com.example.hateoas.domain.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.example.hateoas.dao.bean.Avatar;
import com.example.hateoas.dao.bean.User;
import com.example.hateoas.domain.controller.AvatarController;
import com.example.hateoas.domain.controller.UserController;
import com.example.hateoas.domain.model.UserModel;

@Component
public class UserRepresentationModelAssembler extends AbstractRepresentationModelAssembler<User, UserModel> {

    public UserRepresentationModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    protected Object getEntityId(User entity) {
        return entity.getId();
    }

    @Override
    protected void setToModel(User entity, UserModel model) {
        model.setId(entity.getId());
        model.setName(entity.getName());
        if (entity.getAvatar() != null) {
            model.setAvatarId(entity.getAvatar().getId());
            model.add(linkTo(
                    methodOn(AvatarController.class)
                            .findById(entity.getAvatar().getId()))
                    .withRel("avatar"));
        }
        model.add(linkTo(methodOn(UserController.class)
                .contacts(entity.getId()))
                .withRel("contacts"));
    }

    @Override
    public User toEntity(UserModel model) {
        final User user = new User();
        user.setId(model.getId());
        user.setName(model.getName());
        if (model.getAvatarId() != null) {
            final Avatar avatar = new Avatar();
            avatar.setId(model.getAvatarId());
            user.setAvatar(avatar);
        }
        return user;
    }

}

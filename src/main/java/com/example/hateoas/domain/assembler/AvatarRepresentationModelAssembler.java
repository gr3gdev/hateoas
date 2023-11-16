package com.example.hateoas.domain.assembler;

import org.springframework.stereotype.Component;

import com.example.hateoas.dao.bean.Avatar;
import com.example.hateoas.domain.controller.AvatarController;
import com.example.hateoas.domain.model.AvatarModel;

@Component
public class AvatarRepresentationModelAssembler extends AbstractRepresentationModelAssembler<Avatar, AvatarModel> {

    public AvatarRepresentationModelAssembler() {
        super(AvatarController.class, AvatarModel.class);
    }

    @Override
    protected Object getEntityId(Avatar entity) {
        return entity.getId();
    }

    @Override
    protected void setToModel(Avatar entity, AvatarModel model) {
        model.setId(entity.getId());
        model.setData(entity.getData());
    }

    @Override
    public Avatar toEntity(AvatarModel model) {
        final Avatar avatar = new Avatar();
        avatar.setId(model.getId());
        avatar.setData(model.getData());
        return avatar;
    }

}

package com.example.hateoas.domain.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.hateoas.dao.bean.Avatar;
import com.example.hateoas.dao.repository.AvatarRepository;
import com.example.hateoas.domain.assembler.AvatarRepresentationModelAssembler;
import com.example.hateoas.domain.model.AvatarModel;
import com.example.hateoas.utils.ImageUtils;

@Service
public class AvatarService
        extends AbstractService<Avatar, AvatarRepository, AvatarModel, AvatarRepresentationModelAssembler> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository repository, AvatarRepresentationModelAssembler representationModelAssembler) {
        super(repository, representationModelAssembler);
    }

    @Override
    public AvatarModel save(AvatarModel model) {
        try {
            model.setData(ImageUtils.optimizeImage(model.getData()));
        } catch (IOException e) {
            LOGGER.error("Unable to optimize image", e);
        }
        return super.save(model);
    }

}

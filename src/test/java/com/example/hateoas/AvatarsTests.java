package com.example.hateoas;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.example.hateoas.domain.model.AvatarModel;

public class AvatarsTests extends AbstractApplicationTests<AvatarModel> {

    @Override
    protected String name() {
        return "avatars";
    }

    @Override
    protected Class<AvatarModel> getModelClass() {
        return AvatarModel.class;
    }

    @Override
    protected AvatarModel newModel() {
        final AvatarModel newAvatar = new AvatarModel();
        try {
            newAvatar.setData(
                    AbstractApplicationTests.class.getResourceAsStream("/avatars/avatar2.jpeg").readAllBytes());
        } catch (IOException e) {
            fail(e);
        }
        return newAvatar;
    }

    @Override
    protected AvatarModel updatedModel() {
        final AvatarModel avatar2 = new AvatarModel();
        avatar2.setId(2L);
        try {
            avatar2.setData(AbstractApplicationTests.class.getResourceAsStream("/avatars/avatar1.jpg").readAllBytes());
        } catch (IOException e) {
            fail(e);
        }
        return avatar2;
    }

}

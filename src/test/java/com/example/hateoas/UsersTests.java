package com.example.hateoas;

import com.example.hateoas.domain.model.UserModel;

public class UsersTests extends AbstractApplicationTests<UserModel> {

    @Override
    protected String name() {
        return "users";
    }

    @Override
    protected Class<UserModel> getModelClass() {
        return UserModel.class;
    }

    @Override
    protected UserModel newModel() {
        final UserModel newUser = new UserModel();
        newUser.setName("Nouvel utilisateur");
        return newUser;
    }

    @Override
    protected UserModel updatedModel() {
        final UserModel user2 = new UserModel();
        user2.setId(2L);
        user2.setName("Bradley PASLONG");
        user2.setAvatarId(1L);
        return user2;
    }

}

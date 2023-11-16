package com.example.hateoas.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserModel extends AbstractModel {
    private String name;
    private Long avatarId;
}

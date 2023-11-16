package com.example.hateoas.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AvatarModel extends AbstractModel {
    private byte[] data;
}

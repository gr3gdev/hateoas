package com.example.hateoas.domain.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractModel extends RepresentationModel<AbstractModel> {
    private Long id;
}

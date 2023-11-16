package com.example.hateoas.domain.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.example.hateoas.domain.model.AbstractModel;

public abstract class AbstractRepresentationModelAssembler<E, M extends AbstractModel>
        extends RepresentationModelAssemblerSupport<E, M> {

    public AbstractRepresentationModelAssembler(Class<?> controllerClass, Class<M> resourceType) {
        super(controllerClass, resourceType);
    }

    protected abstract Object getEntityId(E entity);

    protected abstract void setToModel(E entity, M model);

    public abstract E toEntity(M model);

    @Override
    public M toModel(E entity) {
        final M model = createModelWithId(getEntityId(entity), entity);
        setToModel(entity, model);
        return model;
    }

}

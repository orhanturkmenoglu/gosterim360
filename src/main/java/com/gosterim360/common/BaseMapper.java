package com.gosterim360.common;

import java.util.List;

public abstract class BaseMapper<E, R, S> {

    public abstract R toDTO(E entity);

    public abstract E toEntity(S request);

    public List<R> toDTOList(List<E> entities) {
        return entities
                .stream()
                .map(this::toDTO)
                .toList();
    }
}

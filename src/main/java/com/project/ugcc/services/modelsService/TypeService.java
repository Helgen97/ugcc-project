package com.project.ugcc.services.modelsService;

import java.util.Optional;

public interface TypeService<T> extends ModelService<T> {

    Optional<T> getByNamedId(String namedId);

    T setSectionToModel(T t, Long sectionId);

}

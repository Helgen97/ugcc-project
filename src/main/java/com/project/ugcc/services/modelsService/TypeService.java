package com.project.ugcc.services.modelsService;

public interface TypeService<T, D> extends ModelService<T, D> {

    D getByNamedId(String namedId);

    T setSectionToModel(T t, Long sectionId);

}

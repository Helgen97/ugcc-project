package com.project.ugcc.services.modelsService;

public interface TypeService<T> extends ModelService<T> {

    T getByNamedId(String namedId);

    T setSectionToModel(T t, Long sectionId);

}

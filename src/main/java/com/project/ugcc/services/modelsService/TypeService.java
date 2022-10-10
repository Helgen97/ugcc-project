package com.project.ugcc.services.modelsService;

public interface TypeService<T> extends ModelService<T> {

    T setSectionToModel(T t, Long sectionId);

}

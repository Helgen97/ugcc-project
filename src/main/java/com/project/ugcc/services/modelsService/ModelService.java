package com.project.ugcc.services.modelsService;

import java.util.List;

public interface ModelService<T> {

    T getOneById(Long id);

    List<T> getAll();

    T create(T t);

    T update(T t);

    void delete(Long id);

}

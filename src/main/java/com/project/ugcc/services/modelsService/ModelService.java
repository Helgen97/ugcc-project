package com.project.ugcc.services.modelsService;

import java.util.List;

public interface ModelService<T, D> {

    D getOneById(Long id);

    List<D> getAll();

    D create(T t);

    D update(T t);

    void delete(Long id);

}

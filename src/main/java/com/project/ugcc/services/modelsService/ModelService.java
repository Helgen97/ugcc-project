package com.project.ugcc.services.modelsService;

import java.util.List;
import java.util.Optional;

public interface ModelService<T> {

    Optional<T> getOneById(Long id);

    List<T> getAll();

    T create(T t);

    T update(T t);

    void delete(Long id);

}

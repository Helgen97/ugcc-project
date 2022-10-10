package com.project.ugcc.repositories;

import com.project.ugcc.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends TypeRepository<News> {

    Page<News> findAllBySectionID(Long id, Pageable pageable);
}

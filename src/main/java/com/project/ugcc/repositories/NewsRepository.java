package com.project.ugcc.repositories;

import com.project.ugcc.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends TypeRepository<News> {

    Optional<News> findByNamedId(String namedId);

    Page<News> findAllBySectionId(Long id, Pageable pageable);

    Page<News> findAllBySectionNamedId(String namedId, Pageable pageable);

    Page<News> findAllByTitleContainingIgnoreCase(String query, Pageable pageable);
}

package com.project.ugcc.repositories;

import com.project.ugcc.models.Album;
import com.project.ugcc.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends TypeRepository<Article> {

    Optional<Article> findByNamedId(String namedId);

    Page<Article> findAllBySectionID(Long id, Pageable pageable);

}

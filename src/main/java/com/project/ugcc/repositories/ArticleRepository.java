package com.project.ugcc.repositories;

import com.project.ugcc.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends TypeRepository<Article> {

    Page<Article> findAllBySectionID(Long id, Pageable pageable);

}

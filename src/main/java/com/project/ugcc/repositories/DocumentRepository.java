package com.project.ugcc.repositories;

import com.project.ugcc.models.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends TypeRepository<Document> {

    Page<Document> findAllBySectionID(Long id, Pageable pageable);
}

package com.project.ugcc.repositories;

import com.project.ugcc.models.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends TypeRepository<Document> {

    Optional<Document> findByNamedId(String namedId);

    Page<Document> findAllBySectionId(Long id, Pageable pageable);
}

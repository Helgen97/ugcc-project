package com.project.ugcc.repositories;

import com.project.ugcc.models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends TypeRepository<Album> {

    Optional<Album> findByNamedId(String namedId);

    Page<Album> findAllBySectionId(Long id, Pageable pageable);

}

package com.project.ugcc.repositories;

import com.project.ugcc.models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends TypeRepository<Album> {

    Page<Album> findAllBySectionID(Long id, Pageable pageable);

}

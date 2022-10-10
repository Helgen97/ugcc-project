package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.AlbumDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Album;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.AlbumRepository;
import com.project.ugcc.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService implements TypeService<Album> {

    private final AlbumRepository albumRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, SectionRepository sectionRepository) {
        this.albumRepository = albumRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Album> getOneById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Album> getAll() {
        return albumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<AlbumDTO> getPageOfAlbums(int page, int size) {
        Page<Album> albumsPage = albumRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "ID")));
        return albumsPage.map(AlbumDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<AlbumDTO> getPageOfAlbumsBySectionId(Long id, int page, int size) {
        Page<Album> albumsPage = albumRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return albumsPage.map(AlbumDTO::of);
    }

    @Override
    @Transactional
    public Album create(Album album) {
        return albumRepository.save(album);
    }

    @Override
    @Transactional(readOnly = true)
    public Album setSectionToModel(Album album, Long id) {
        Section section = sectionRepository.findByID(id).orElseThrow(() -> new NotFoundException(String.format("Section with ID: %s - not found", id)));
        album.setSection(section);
        return album;
    }

    @Override
    @Transactional
    public Album update(Album album) {
        return albumRepository.save(album);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        albumRepository.deleteById(id);
    }
}

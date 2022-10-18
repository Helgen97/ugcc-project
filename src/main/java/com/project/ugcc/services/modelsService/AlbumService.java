package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.AlbumDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Album;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.AlbumRepository;
import com.project.ugcc.repositories.SectionRepository;
import com.project.ugcc.services.fileService.FileStorageService;
import com.project.ugcc.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlbumService implements TypeService<Album> {

    private final static Logger LOGGER = LogManager.getLogger(AlbumService.class.getName());

    private final AlbumRepository albumRepository;
    private final SectionRepository sectionRepository;
    private final FileStorageService fileService;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, SectionRepository sectionRepository, FileStorageService fileService) {
        this.albumRepository = albumRepository;
        this.sectionRepository = sectionRepository;
        this.fileService = fileService;
    }

    @Override
    @Transactional(readOnly = true)
    public Album getOneById(Long id) {
        LOGGER.info(String.format("Getting album by id. Album id: %d", id));
        return albumRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("No Album wit id %s", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Album getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting album by named id. Named id: %s", namedId));
        return albumRepository.findByNamedId(namedId).orElseThrow(() -> new NotFoundException(String.format("No Album wit named id %s", namedId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Album> getAll() {
        LOGGER.info("Getting all albums");
        return albumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<AlbumDTO> getPageOfAlbums(int page, int size) {
        LOGGER.info(String.format("Get page of albums. Page: %d. Size: %d", page, size));
        Page<Album> albumsPage = albumRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return albumsPage.map(AlbumDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<AlbumDTO> getPageOfAlbumsBySectionId(Long id, int page, int size) {
        LOGGER.info(String.format("Get page of albums by section id. Section id: %d. Page: %d. Size: %d", id, page, size));
        Page<Album> albumsPage = albumRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return albumsPage.map(AlbumDTO::of);
    }

    @Override
    @Transactional
    public Album create(Album album) {
        LOGGER.info("Creating new album");
        album.setNamedId(Utils.transliterateStringFromCyrillicToLatinChars(album.getTitle()));
        album.setCreationDate(LocalDateTime.now());
        return albumRepository.save(album);
    }

    @Override
    @Transactional(readOnly = true)
    public Album setSectionToModel(Album album, Long id) {
        LOGGER.info(String.format("Setting section to album. Section id: %d", id));
        Section section = sectionRepository.findByID(id).orElseThrow(() -> new NotFoundException(String.format("Section with id %d not found", id)));
        album.setSection(section);
        return album;
    }

    @Override
    @Transactional
    public Album update(Album album) {
        LOGGER.info(String.format("Updating album. Album id: %d", album.getID()));
        Album albumToUpdate = albumRepository.findById(album.getID()).orElseThrow(() -> new NotFoundException(String.format("Album with id %d not found", album.getID())));
        albumToUpdate.getImagesUrls().forEach(url -> {
            if (!album.getImagesUrls().contains(url)) {
                fileService.deleteFile(url);
            }
        });
        return albumRepository.save(album);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting album. Album id: %d", id));

        Album albumToDelete = albumRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Album with id %d not found", id)));

        albumToDelete.getImagesUrls().forEach(fileService::deleteFile);
        albumRepository.delete(albumToDelete);
    }
}

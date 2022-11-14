package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.AlbumDTO;
import com.project.ugcc.models.Album;
import com.project.ugcc.services.modelsService.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    public AlbumDTO getOneById(@PathVariable Long id) {
        return albumService.getOneById(id);
    }

    @GetMapping
    public List<AlbumDTO> getAll() {
        return albumService.getAll();
    }

    @GetMapping("/pages")
    public Page<AlbumDTO> getPageOfAlbums(@RequestParam(required = false) Long sectionId,
                                          @RequestParam int page,
                                          @RequestParam int size) {
        return sectionId == null
                ?
                albumService.getPageOfAlbums(page, size)
                :
                albumService.getPageOfAlbumsBySectionId(sectionId, page, size);
    }

    @PostMapping
    public AlbumDTO create(@RequestBody Album album, @RequestParam Long sectionId) {
        Album albumWithSection = albumService.setSectionToModel(album, sectionId);
        return albumService.create(albumWithSection);
    }

    @PutMapping
    public AlbumDTO update(@RequestBody Album album) {
        return albumService.update(album);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        albumService.delete(id);
    }
}

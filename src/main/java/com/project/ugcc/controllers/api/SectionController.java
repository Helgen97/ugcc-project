package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.SectionDTO;
import com.project.ugcc.models.Section;
import com.project.ugcc.services.modelsService.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sections")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionDTO> getAll(@RequestParam(required = false) String category) {
        return category == null
                ?
                sectionService.getAll()
                :
                sectionService.getAllByCategory(category);
    }

    @GetMapping("/{id}")
    public SectionDTO getOneById(@PathVariable long id) {
        return sectionService.getOneById(id);
    }

    @PostMapping
    public SectionDTO createSection(@RequestBody Section section) {
        return sectionService.create(section);
    }

    @PutMapping
    public SectionDTO updateSection(@RequestBody Section section) {
        return sectionService.update(section);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable long id) {
        sectionService.delete(id);
    }
}

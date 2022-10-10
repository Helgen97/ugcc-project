package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.SectionDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Section;
import com.project.ugcc.services.modelsService.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
                sectionService.getAll().stream().map(SectionDTO::of).collect(Collectors.toList())
                :
                sectionService.getAllByCategory(category).stream().map(SectionDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SectionDTO getOneById(@PathVariable long id) {
        return SectionDTO.of(sectionService.getOneById(id).orElseThrow(() -> new NotFoundException("Section not found!")));
    }

    @PostMapping
    public SectionDTO createSection(@RequestBody Section section) {
        return SectionDTO.of(sectionService.create(section));
    }

    @PutMapping
    public SectionDTO updateSection(@RequestBody Section section) {
        return SectionDTO.of(sectionService.update(section));
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable long id) {
        sectionService.delete(id);
    }
}

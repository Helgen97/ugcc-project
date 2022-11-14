package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.SectionDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.SectionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService implements ModelService<Section, SectionDTO> {

    private final static Logger LOGGER = LogManager.getLogger(SectionService.class.getName());

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public SectionDTO getOneById(Long id) {
        LOGGER.info(String.format("Getting section by id. Section id: %d", id));
        return SectionDTO.of(sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Section with id %d not found", id))));
    }

    @Transactional(readOnly = true)
    public SectionDTO getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting section by namedId. NamedId: %s.", namedId));
        return SectionDTO.of(sectionRepository.findByNamedId(namedId).orElseThrow(() -> new NotFoundException(String.format("Section with namedId %s not found", namedId))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectionDTO> getAll() {
        LOGGER.info("Getting all sections");
        return sectionRepository.findAll().stream().map(SectionDTO::of).collect(Collectors.toList());
    }

    @Transactional
    public List<SectionDTO> getAllByCategory(String category) {
        LOGGER.info(String.format("Getting all sections by category. Category: %s", category));
        return sectionRepository.findAllByCategory(Category.valueOf(category)).stream().map(SectionDTO::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SectionDTO create(Section section) {
        LOGGER.info("Creating new section");
        return SectionDTO.of(sectionRepository.save(section));
    }

    @Override
    @Transactional
    public SectionDTO update(Section section) {
        LOGGER.info(String.format("Updating section. Section id %d", section.getId()));
        return SectionDTO.of(sectionRepository.save(section));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting section. Section id: %d", id));
        sectionRepository.deleteById(id);
    }
}

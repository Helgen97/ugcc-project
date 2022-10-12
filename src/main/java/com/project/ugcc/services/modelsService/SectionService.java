package com.project.ugcc.services.modelsService;

import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.SectionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService implements ModelService<Section> {

    private final static Logger LOGGER = LogManager.getLogger(SectionService.class.getName());

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Section> getOneById(Long id) {
        LOGGER.info(String.format("Getting section by id: %d", id));
        return sectionRepository.findByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Section> getAll() {
        LOGGER.info("Getting all sections");
        return sectionRepository.findAll();
    }

    @Transactional
    public List<Section> getAllByCategory(String category) {
        LOGGER.info(String.format("Getting all sections by category. Category: %s", category));
        return sectionRepository.findAllByCategory(Category.valueOf(category));
    }

    @Override
    @Transactional
    public Section create(Section section) {
        LOGGER.info("Creating new section");
        return sectionRepository.save(section);
    }

    @Override
    @Transactional
    public Section update(Section section) {
        LOGGER.info(String.format("Updating section with id: %d", section.getID()));
        return sectionRepository.save(section);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting section with id: %d", id));
        sectionRepository.deleteByID(id);
    }
}

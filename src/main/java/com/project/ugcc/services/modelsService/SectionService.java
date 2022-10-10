package com.project.ugcc.services.modelsService;

import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService implements ModelService<Section> {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Section> getOneById(Long id) {
        return sectionRepository.findByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Section> getAll() {
        return sectionRepository.findAll();
    }

    @Transactional
    public List<Section> getAllByCategory(String category){
        return sectionRepository.findAllByCategory(Category.valueOf(category));
    }

    @Override
    @Transactional
    public Section create(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    @Transactional
    public Section update(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sectionRepository.deleteByID(id);
    }
}

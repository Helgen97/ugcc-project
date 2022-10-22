package com.project.ugcc.repositories;

import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    Optional<Section> findById(Long id);

    Optional<Section> findByNamedId(String namedId);

    void deleteById(Long id);

    List<Section> findAllByCategory(Category category);

}

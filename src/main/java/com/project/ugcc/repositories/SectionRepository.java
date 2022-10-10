package com.project.ugcc.repositories;

import com.project.ugcc.models.Category;
import com.project.ugcc.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    Optional<Section> findByID(Long id);

    void deleteByID(Long id);

    List<Section> findAllByCategory(Category category);

}

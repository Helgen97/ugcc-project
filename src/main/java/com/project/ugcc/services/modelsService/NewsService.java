package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.NewsDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.News;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.NewsRepository;
import com.project.ugcc.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService implements TypeService<News> {

    private final NewsRepository newsRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, SectionRepository sectionRepository) {
        this.newsRepository = newsRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<News> getOneById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<News> getAll() {
        return newsRepository.findAll(Sort.by(Sort.Direction.DESC, "ID"));
    }

    @Transactional(readOnly = true)
    public Page<NewsDTO> getPageOfNews(int page, int size) {
        Page<News> newsPage = newsRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return newsPage.map(NewsDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<NewsDTO> getPageOfNewsBySectionId(Long id, int page, int size) {
        Page<News> newsPage = newsRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return newsPage.map(NewsDTO::of);
    }

    @Override
    @Transactional
    public News create(News news) {
        news.setDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News setSectionToModel(News news, Long id) {
        Section sectionFromDB = sectionRepository.findByID(id).orElseThrow(() -> new NotFoundException(String.format("Section with id = %s not found", id)));
        news.setSection(sectionFromDB);
        return news;
    }

    @Override
    @Transactional
    public News update(News news) {
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }
}

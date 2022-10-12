package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.NewsDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.News;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.NewsRepository;
import com.project.ugcc.repositories.SectionRepository;
import com.project.ugcc.utils.UrlConverter;
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
import java.util.Optional;

@Service
public class NewsService implements TypeService<News> {

    private final static Logger LOGGER = LogManager.getLogger(NewsService.class.getName());

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
        LOGGER.info(String.format("Getting news by id: %d", id));
        return newsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<News> getAll() {
        LOGGER.info("Getting all news");
        return newsRepository.findAll(Sort.by(Sort.Direction.DESC, "ID"));
    }

    @Override
    public Optional<News> getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting news by named id. Named id: %s", namedId));
        return newsRepository.findByNamedId(namedId);
    }

    @Transactional(readOnly = true)
    public Page<NewsDTO> getPageOfNews(int page, int size) {
        LOGGER.info(String.format("Get page of news. Page: %d. Size: %d", page, size));
        Page<News> newsPage = newsRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return newsPage.map(NewsDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<NewsDTO> getPageOfNewsBySectionId(Long id, int page, int size) {
        LOGGER.info(String.format("Get page of news by section id. Section id: %d. Page: %d. Size: %d", id, page, size));
        Page<News> newsPage = newsRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return newsPage.map(NewsDTO::of);
    }

    @Override
    @Transactional
    public News create(News news) {
        LOGGER.info("Creating new news");
        news.setNamedId(UrlConverter.urlTransliterate(news.getTitle()));
        news.setDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News setSectionToModel(News news, Long id) {
        LOGGER.info(String.format("Setting section to new news. Section id: %d", id));
        Section sectionFromDB = sectionRepository.findByID(id).orElseThrow(() -> new NotFoundException(String.format("Section with id = %s not found", id)));
        news.setSection(sectionFromDB);
        return news;
    }

    @Override
    @Transactional
    public News update(News news) {
        LOGGER.info(String.format("Updating news with id: %d", news.getID()));
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting news with id: %d", id));
        newsRepository.deleteById(id);
    }
}

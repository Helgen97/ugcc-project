package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.NewsDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.News;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.NewsRepository;
import com.project.ugcc.repositories.SectionRepository;
import com.project.ugcc.services.fileService.FileStorageService;
import com.project.ugcc.utils.Utils;
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

@Service
public class NewsService implements TypeService<News> {

    private final static Logger LOGGER = LogManager.getLogger(NewsService.class.getName());

    private final NewsRepository newsRepository;
    private final SectionRepository sectionRepository;
    private final FileStorageService fileService;

    @Autowired
    public NewsService(NewsRepository newsRepository,
                       SectionRepository sectionRepository,
                       FileStorageService fileStorageService) {
        this.newsRepository = newsRepository;
        this.sectionRepository = sectionRepository;
        this.fileService = fileStorageService;
    }

    @Override
    @Transactional(readOnly = true)
    public News getOneById(Long id) {
        LOGGER.info(String.format("Getting news by id. News id: %d", id));
        return newsRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("News with id %s not found!", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<News> getAll() {
        LOGGER.info("Getting all news");
        return newsRepository.findAll(Sort.by(Sort.Direction.DESC, "ID"));
    }

    @Override
    public News getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting news by named id. Named id: %s", namedId));
        return newsRepository.findByNamedId(namedId).orElseThrow(() -> new NotFoundException(String.format("News with named id %s not found!", namedId)));
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
        news.setNamedId(Utils.transliterateStringFromCyrillicToLatinChars(news.getTitle()));
        news.setDate(LocalDateTime.now());
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public News setSectionToModel(News news, Long id) {
        LOGGER.info(String.format("Setting section to news. Section id: %d", id));
        Section sectionFromDB = sectionRepository.findByID(id).orElseThrow(() -> new NotFoundException(String.format("Section with id %s not found", id)));
        news.setSection(sectionFromDB);
        return news;
    }

    @Override
    @Transactional
    public News update(News news) {
        LOGGER.info(String.format("Updating news. News id: %d", news.getID()));

        News newsToUpdate = newsRepository.findById(news.getID()).orElseThrow(() -> new NotFoundException(String.format("News with id %s not found!", news.getID())));
        if (!newsToUpdate.getImageURL().equals(news.getImageURL())) {
            fileService.deleteFile(newsToUpdate.getImageURL());
        }
        return newsRepository.save(news);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting news. News id: %d", id));

        News newsToDelete = newsRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("News with id %s not found!", id)));
        fileService.deleteFile(newsToDelete.getImageURL());
        newsRepository.delete(newsToDelete);
    }
}

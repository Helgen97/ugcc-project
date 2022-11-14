package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.ArticleDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Article;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.ArticleRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService implements TypeService<Article, ArticleDTO> {

    private final static Logger LOGGER = LogManager.getLogger(ArticleService.class.getName());

    private final ArticleRepository articleRepository;
    private final SectionRepository sectionRepository;
    private final FileStorageService fileService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, SectionRepository sectionRepository, FileStorageService fileStorageService) {
        this.articleRepository = articleRepository;
        this.sectionRepository = sectionRepository;
        this.fileService = fileStorageService;
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDTO getOneById(Long id) {
        LOGGER.info(String.format("Getting article by id. Article id: %d.", id));
        return ArticleDTO.of(articleRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Article with id %d not found", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDTO getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting article by named id. Named id: %s", namedId));
        return ArticleDTO.of(articleRepository.findByNamedId(namedId).orElseThrow(() -> new NotFoundException(String.format("Article with id %s not found", namedId))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDTO> getAll() {
        LOGGER.info("Getting all articles.");
        return articleRepository.findAll().stream().map(ArticleDTO::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticleDTO> getFourRandomArticleExceptArticleWithId(Long id) {
        List<Article> articleList = articleRepository.findAll().stream().filter(article -> !article.getId().equals(id)).collect(Collectors.toList());
        Collections.shuffle(articleList);
        return articleList.stream().limit(4).map(ArticleDTO::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ArticleDTO> getPageOfArticles(int page, int size) {
        LOGGER.info(String.format("Get page of articles. Page: %d. Size: %d", page, size));
        Page<Article> articlePage = articleRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return articlePage.map(ArticleDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDTO> getPageOfArticlesBySectionId(Long id, int page, int size) {
        LOGGER.info(String.format("Get page of articles by section id. Section id: %d. Page: %d. Size: %d", id, page, size));
        Page<Article> articlePage = articleRepository.findAllBySectionId(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return articlePage.map(ArticleDTO::of);
    }

    @Override
    @Transactional
    public ArticleDTO create(Article article) {
        LOGGER.info("Creating new article");
        article.setNamedId(Utils.transliterateStringFromCyrillicToLatinChars(article.getTitle()));
        return ArticleDTO.of(articleRepository.save(article));
    }

    @Override
    @Transactional(readOnly = true)
    public Article setSectionToModel(Article article, Long id) {
        LOGGER.info(String.format("Setting section to article. Section id: %d", id));
        Section section = sectionRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Section with ID: %s - not found", id)));
        article.setSection(section);
        return article;
    }

    @Override
    @Transactional
    public ArticleDTO update(Article article) {
        LOGGER.info(String.format("Updating article. Article id: %d", article.getId()));

        Article articleToUpdate = articleRepository.findById(article.getId()).orElseThrow(() -> new NotFoundException(String.format("Article with id %s not found!", article.getId())));
        if (!articleToUpdate.getImageUrl().equals(article.getImageUrl()) && !articleToUpdate.getImageUrl().isEmpty()) {
            fileService.deleteFile(articleToUpdate.getImageUrl());
        }
        return ArticleDTO.of(articleRepository.save(article));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting article. Article id: %d", id));

        Article articleToDelete = articleRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Article with id %s not found!", id)));
        fileService.deleteFile(articleToDelete.getImageUrl());
        articleRepository.delete(articleToDelete);
    }
}

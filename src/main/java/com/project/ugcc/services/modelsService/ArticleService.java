package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.ArticleDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Article;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.ArticleRepository;
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

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService implements TypeService<Article> {

    private final static Logger LOGGER = LogManager.getLogger(ArticleService.class.getName());

    private final ArticleRepository articleRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, SectionRepository sectionRepository) {
        this.articleRepository = articleRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> getOneById(Long id) {
        LOGGER.info(String.format("Getting article by id: %d.", id));
        return articleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting article by named id. Named id: %s", namedId));
        return articleRepository.findByNamedId(namedId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAll() {
        LOGGER.info("Getting all articles.");
        return articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDTO> getPageOfArticles(int page, int size) {
        LOGGER.info(String.format("Get page of articles. Page: %d. Size: %d", page, size));
        Page<Article> articlePage = articleRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "ID")));
        return articlePage.map(ArticleDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDTO> getPageOfArticlesBySectionId(Long id, int page, int size) {
        LOGGER.info(String.format("Get page of articles by section id. Section id: %d. Page: %d. Size: %d", id, page, size));
        Page<Article> articlePage = articleRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return articlePage.map(ArticleDTO::of);
    }

    @Override
    @Transactional
    public Article create(Article article) {
        LOGGER.info("Creating new article");
        article.setNamedId(UrlConverter.urlTransliterate(article.getTitle()));
        return articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Article setSectionToModel(Article article, Long id) {
        LOGGER.info(String.format("Setting section to new article. Section id: %d", id));
        Section section = sectionRepository.findByID(id).orElseThrow(() -> new NotFoundException(String.format("Section with ID: %s - not found", id)));
        article.setSection(section);
        return article;
    }

    @Override
    @Transactional
    public Article update(Article article) {
        LOGGER.info(String.format("Updating article with id: %d", article.getID()));
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting album with id: %d", id));
        articleRepository.deleteById(id);
    }
}

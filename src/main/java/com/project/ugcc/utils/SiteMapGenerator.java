package com.project.ugcc.utils;

import com.project.ugcc.models.Category;
import com.project.ugcc.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteMapGenerator {

    private final static Logger LOGGER = LogManager.getLogger(SiteMapGenerator.class.getName());

    private final NewsRepository newsRepository;
    private final SectionRepository sectionRepository;
    private final DocumentRepository documentRepository;
    private final AlbumRepository albumRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public SiteMapGenerator(
            NewsRepository newsRepository,
            SectionRepository sectionRepository,
            DocumentRepository documentRepository,
            AlbumRepository albumRepository,
            ArticleRepository articleRepository) {
        this.newsRepository = newsRepository;
        this.sectionRepository = sectionRepository;
        this.documentRepository = documentRepository;
        this.albumRepository = albumRepository;
        this.articleRepository = articleRepository;
    }

    public String generateSiteMap(String baseUrl) {
        LOGGER.info("Generating sitemap.");
        StringBuffer siteMap = new StringBuffer();
        siteMap.append(baseUrl).append("\n");
        siteMap.append(baseUrl).append("/news\n");
        newsRepository.findAll().forEach((news -> siteMap.append(baseUrl).append("/news/").append(news.getNamedId()).append("\n")));
        sectionRepository.findAllByCategory(Category.NEWS).forEach((section -> siteMap.append(baseUrl).append("/news/section/").append(section.getNamedId()).append("\n")));
        siteMap.append(baseUrl).append("/search\n");
        siteMap.append(baseUrl).append("/documents\n");
        documentRepository.findAll().forEach((document -> siteMap.append(baseUrl).append("/documents/").append(document.getNamedId()).append("\n")));
        articleRepository.findAll().forEach(article -> siteMap.append(baseUrl).append("/article/").append(article.getNamedId()).append("\n"));
        siteMap.append(baseUrl).append("/kahetyzm-ugcc\n");
        siteMap.append(baseUrl).append("/kahehyzm\n");
        siteMap.append(baseUrl).append("/gallery\n");
        albumRepository.findAll().forEach(album -> siteMap.append(baseUrl).append("/album/").append(album.getNamedId()).append("\n"));
        siteMap.append(baseUrl).append("/contacts\n");

        return siteMap.toString();
    }
}

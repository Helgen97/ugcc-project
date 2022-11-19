package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.ArticleDTO;
import com.project.ugcc.models.Article;
import com.project.ugcc.services.modelsService.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ArticleDTO getOneById(@PathVariable Long id) {
        return articleService.getOneById(id);
    }

    @GetMapping
    public List<ArticleDTO> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/pages")
    public Page<ArticleDTO> getPageOfArticles(@RequestParam(required = false) Long sectionId,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        return sectionId == null
                ?
                articleService.getPageOfArticles(page, size)
                :
                articleService.getPageOfArticlesBySectionId(sectionId, page, size);
    }

    @GetMapping("/pages/random")
    public Page<ArticleDTO> getFourRandomDocuments() {
        return articleService.getFourRandomArticles();
    }

    @PostMapping
    public ArticleDTO create(@RequestBody Article article, @RequestParam Long sectionId) {
        Article articleWithSection = articleService.setSectionToModel(article, sectionId);
        return articleService.create(articleWithSection);
    }

    @PutMapping
    public ArticleDTO update(@RequestBody Article article) {
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }

}

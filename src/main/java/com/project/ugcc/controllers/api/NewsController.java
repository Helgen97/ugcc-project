package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.NewsDTO;
import com.project.ugcc.models.News;
import com.project.ugcc.services.modelsService.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<NewsDTO> getAll() {
        return newsService.getAll();
    }

    @GetMapping("/{id}")
    public NewsDTO getOne(@PathVariable Long id) {
        return newsService.getOneById(id);
    }

    @GetMapping("/pages/random")
    public Page<NewsDTO> getFourRandomNews() {
        return newsService.getFourRandomNews();
    }

    @GetMapping("/pages")
    public Page<NewsDTO> getNewsPage(@RequestParam(required = false) Long sectionId,
                                     @RequestParam int page,
                                     @RequestParam int size) {
        return sectionId == null
                ?
                newsService.getPageOfNews(page, size)
                :
                newsService.getPageOfNewsBySectionId(sectionId, page, size);
    }

    @PostMapping
    public NewsDTO createNews(@RequestBody News news,
                              @RequestParam Long sectionId) {
        News newsWithSection = newsService.setSectionToModel(news, sectionId);
        return newsService.create(newsWithSection);
    }

    @PutMapping
    public NewsDTO updateNews(@RequestBody News news) {
        return newsService.update(news);
    }

    @DeleteMapping("/{id}")
    public void createNews(@PathVariable Long id) {
        newsService.delete(id);
    }
}

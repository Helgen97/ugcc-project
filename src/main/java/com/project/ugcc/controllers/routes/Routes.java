package com.project.ugcc.controllers.routes;

import com.project.ugcc.services.modelsService.ArticleService;
import com.project.ugcc.services.modelsService.DocumentService;
import com.project.ugcc.services.modelsService.NewsService;
import com.project.ugcc.services.modelsService.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Routes {

    private final SectionService sectionService;
    private final NewsService newsService;
    private final DocumentService documentService;
    private final ArticleService articleService;

    @Autowired
    public Routes(SectionService sectionService, NewsService newsService, DocumentService documentService, ArticleService articleService) {
        this.sectionService = sectionService;
        this.newsService = newsService;
        this.documentService = documentService;
        this.articleService = articleService;
    }

    @GetMapping("/a-panel")
    public String aPanelPage(Model model) {
        model.addAttribute("newsSections", sectionService.getAllByCategory("NEWS"));
        model.addAttribute("newsList", newsService.getPageOfNews(0, 10));
        model.addAttribute("documentSections", sectionService.getAllByCategory("DOCUMENTS"));
        model.addAttribute("documentsList", documentService.getPageOfDocuments(0, 10));
        model.addAttribute("articlesList", articleService.getAll());
        return "panel";
    }
}

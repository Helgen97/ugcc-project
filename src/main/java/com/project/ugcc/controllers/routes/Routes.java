package com.project.ugcc.controllers.routes;

import com.project.ugcc.services.modelsService.ArticleService;
import com.project.ugcc.services.modelsService.DocumentService;
import com.project.ugcc.services.modelsService.NewsService;
import com.project.ugcc.services.modelsService.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/login")
    public String login(@RequestParam(required = false) boolean error,
                        @RequestParam(required = false) boolean logout,
                        Model model) {
        if (error) {
            model.addAttribute("message", "Невірний логін або пароль!");
        }
        if(logout) {
            model.addAttribute("message", "Вихід успішно виконан");
        }
        return "login.html";
    }

    @GetMapping("/a-panel")
    public String aPanelPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("newsSections", sectionService.getAllByCategory("NEWS"));
        model.addAttribute("newsList", newsService.getPageOfNews(0, 10));
        model.addAttribute("documentSections", sectionService.getAllByCategory("DOCUMENTS"));
        model.addAttribute("documentsList", documentService.getPageOfDocuments(0, 10));
        model.addAttribute("articlesList", articleService.getAll());
        return "panel.html";
    }
}

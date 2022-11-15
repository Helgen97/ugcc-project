package com.project.ugcc.controllers.routes;

import com.project.ugcc.DTO.*;
import com.project.ugcc.services.modelsService.*;
import com.project.ugcc.utils.SiteMapGenerator;
import com.project.ugcc.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Controller
public class Routes {

    private final SectionService sectionService;
    private final NewsService newsService;
    private final DocumentService documentService;
    private final ArticleService articleService;
    private final AlbumService albumService;
    private final ContactsService contactsService;
    private final SiteMapGenerator siteMapGenerator;

    @Autowired
    public Routes(SectionService sectionService,
                  NewsService newsService,
                  DocumentService documentService,
                  ArticleService articleService,
                  AlbumService albumService,
                  ContactsService contactService,
                  SiteMapGenerator siteMapGenerator
    ) {
        this.sectionService = sectionService;
        this.newsService = newsService;
        this.documentService = documentService;
        this.articleService = articleService;
        this.albumService = albumService;
        this.contactsService = contactService;
        this.siteMapGenerator = siteMapGenerator;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("title", "Донецький екзархат - Українська Греко-Католицька Церква");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("newsPageOfExarchate", newsService.getPageOfNewsBySectionId(1L, 0, 5));
        model.addAttribute("newsPageOfChurch", newsService.getPageOfNewsBySectionId(2L, 0, 4));
        model.addAttribute("newsPageOfPublications", newsService.getPageOfNewsBySectionId(3L, 0, 4));
        return "index";
    }

    @GetMapping("/news")
    public String allNewsPage(Model model) {
        model.addAttribute("title", "Останні новини за розділами - Донецький екзархат - УКГЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Останні новини за розділами. Новини Донецького екзархату. Україньська Греко-Католицька Церква.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("pagesOfNewsBySections", List.of(
                newsService.getPageOfNewsBySectionId(1L, 0, 5),
                newsService.getPageOfNewsBySectionId(2L, 0, 5),
                newsService.getPageOfNewsBySectionId(3L, 0, 5)
        ));
        return "news";
    }

    @GetMapping("/news/{namedId}")
    public String newsPage(Model model, @PathVariable String namedId) {
        NewsDTO news = newsService.getByNamedId(namedId);
        model.addAttribute("title", news.getTitle());
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", news.getDescription());
        model.addAttribute("metaImage", news.getImageUrl());
        model.addAttribute("mainNews", news);
        model.addAttribute("otherNews", newsService.getPageOfNewsBySectionId(news.getSection().getId(), 0, 4));
        return "news-page";
    }

    @GetMapping("/news/section/{namedId}")
    public String newsBySectionPage(Model model,
                                    @PathVariable String namedId,
                                    @RequestParam(required = false, defaultValue = "0") int page) {
        SectionDTO section = sectionService.getByNamedId(namedId);
        model.addAttribute("title", section.getTitle() + " - Донецький екзархат - УГКЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", String.format("Новини за розділом. %s. Донецький Екзархат Української Греко-Католицької Церкви.", section.getTitle()));
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("pageTitle", section.getTitle());
        model.addAttribute("namedId", namedId);
        model.addAttribute("newsPage", newsService.getPageOfNewsBySectionNamedId(namedId, page, 6));
        return "news-list";
    }

    @GetMapping("/search")
    public String searchPage(Model model,
                             @RequestParam String query,
                             @RequestParam(required = false, defaultValue = "0") int page) {
        model.addAttribute("title", "Донецький екзархат - Українська Греко-Католицька Церква");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("pageTitle", "Результати пошуку:");
        model.addAttribute("query", query);
        model.addAttribute("newsPage", newsService.getPageOfNewsBySearchQuery(query, page, 6));
        return "news-list";
    }

    @GetMapping("/documents")
    public String documentsPage(Model model) {
        Map<String, List<DocumentDTO>> documents = documentService.getAllDocumentsFilteredBySection();

        model.addAttribute("title", "Документи - Донецький екзархат - Українська Греко-Католицька Церква");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("documentsOfExarchate", documents.get("documentsOfExarchate"));
        model.addAttribute("documentsOfChurch", documents.get("documentsOfChurch"));
        return "documents";
    }

    @GetMapping("/documents/{namedId}")
    public String documentPage(Model model, @PathVariable String namedId) {
        DocumentDTO document = documentService.getByNamedId(namedId);

        model.addAttribute("title", document.getTitle() + " - Донецький екзархат - УГКЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("mainDocument", document);
        model.addAttribute("otherDocuments", documentService.getListWithMainDocumentAndFourRandomDocuments(namedId));
        return "document";
    }

    @GetMapping("/article/{namedId}")
    public String articlePage(Model model, @PathVariable String namedId) {
        ArticleDTO article = articleService.getByNamedId(namedId);
        model.addAttribute("title", article.getTitle() + " - Донецький екзархат - УГКЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("article", article);
        model.addAttribute("otherArticles", articleService.getFourRandomArticleExceptArticleWithId(article.getId()));
        return "article";
    }

    @GetMapping("/kahetyzm-ugcc")
    public String kahetyzmUgccPage(Model model) {
        model.addAttribute("title", "Катехизм “Христос — наша Пасха” - Донецький екзархат - УКГЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description",
                "Катехизм “Христос — наша Пасха” - офіційний текст віровчення Української Греко-Католицької Церкви. Книга розкриває особливість богословської традиції УГКЦ, спираючись на Святе Письмо, спадщину Отців Церкви, душпастирів УГКЦ, рішень Вселенських та Помісних Соборів, богослужбових творів, та ін.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        return "kahetyzm-ugcc";
    }

    @GetMapping("/kahehyzm")
    public String kahetyzmPage(Model model) {
        model.addAttribute("title", "Катехизм Католицької Церкви (ККЦ) - Донецький екзархат - УГКЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description",
                "Катехизм Католицької Церкви (ККЦ) — книга, що містить стислий виклад основних положень католицької віри. Катехизм пропонує органічний і синтетичний виклад найважливішого змісту католицького віровчення – як у тому, що стосується віри, так і в питаннях моралі – у світлі ІІ Ватиканського собору і всієї церковної Традиції. Його головні джерела – це Святе Письмо, тексти Отців, літургія й учення Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        return "kahetyzm";
    }

    @GetMapping("/gallery")
    public String galleryPage(Model model,
                              @RequestParam(defaultValue = "0", required = false) int page) {
        model.addAttribute("title", "Галерея - Донецький екзархат - УГКЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("albums", albumService.getPageOfAlbums(page, 9));
        return "gallery";
    }

    @GetMapping("/albums/{namedId}")
    public String albumPage(Model model, @PathVariable String namedId) {
        AlbumDTO album = albumService.getByNamedId(namedId);
        model.addAttribute("title", "Альбом - " + album.getTitle());
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Альбом - " + album.getTitle() + ". Створено: " + album.getCreationDate());
        model.addAttribute("metaImage", album.getImagesUrls().get(0));
        model.addAttribute("mainAlbum", album);
        model.addAttribute("otherAlbums", albumService.getThreeRandomAlbumExceptAlbumWithId(album.getId()));
        return "album";
    }

    @GetMapping("/contacts")
    public String contactsPage(Model model) {
        model.addAttribute("title", "Наші контакти - Донецький екзархат - УГКЦ");
        model.addAttribute("url", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString());
        model.addAttribute("description", "Головна сторінка Донецького Екзархату Української Греко-Католицької Церкви.");
        model.addAttribute("metaImage", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/imgs/MetaImage.png");
        model.addAttribute("contacts", contactsService.getOneById(1L));
        return "contacts";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) boolean error,
                            @RequestParam(required = false) boolean logout,
                            Model model) {
        if (error) {
            model.addAttribute("message", "Невірний логін або пароль!");
        }
        if (logout) {
            model.addAttribute("message", "Вихід успішно виконано");
        }
        return "login";
    }

    @GetMapping("/a-panel")
    public String aPanelPage(Model model) {
        model.addAttribute("articlesList", articleService.getAll());
        return "panel";
    }

    @GetMapping(value = "/sitemap.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String sitemap() {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return siteMapGenerator.generateSiteMap(baseUrl);
    }

    @GetMapping(value = "robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String robots() {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return Utils.generateRobotsFile(baseUrl);
    }
}

package com.project.ugcc.utils;

import com.project.ugcc.models.Category;
import com.project.ugcc.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Service
public class SiteMapGenerator {

    private final static Logger LOGGER = LogManager.getLogger(SiteMapGenerator.class.getName());

    private final NewsRepository newsRepository;
    private final SectionRepository sectionRepository;
    private final DocumentRepository documentRepository;
    private final AlbumRepository albumRepository;
    private final ArticleRepository articleRepository;

    private final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private Transformer transformer;
    private Document sitemap;

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

    public String generateSiteMap(String url) {
        LOGGER.info("Generating sitemap.");
        prepareWriter();
        fillSiteMap(url);
        return getXmlToString();
    }

    private void prepareWriter() {
        try {
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            sitemap = documentBuilder.newDocument();

            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Node createUrlNode(String url, String changeFreq) {
        Element urlNode = sitemap.createElement("url");

        Element locNode = sitemap.createElement("loc");
        locNode.setTextContent(url);

        Element changeFreqNode = sitemap.createElement("changefreq");
        changeFreqNode.setTextContent(changeFreq);

        urlNode.appendChild(locNode);
        urlNode.appendChild(changeFreqNode);

        return urlNode;
    }

    public void fillSiteMap(String baseUrl) {

        Element urlSetNode = sitemap.createElement("urlset");
        urlSetNode.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        sitemap.appendChild(urlSetNode);

        urlSetNode.appendChild(createUrlNode(baseUrl, "daily"));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/news", "weekly"));
        newsRepository.findAll().forEach((news -> urlSetNode.appendChild(createUrlNode(baseUrl + "/news/" + news.getNamedId(), "never"))));
        sectionRepository.findAllByCategory(Category.NEWS).forEach((section -> urlSetNode.appendChild(createUrlNode(baseUrl + "/news/section/" + section.getNamedId(), "weekly"))));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/search", "never"));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/documents", "monthly"));
        documentRepository.findAll().forEach((document -> urlSetNode.appendChild(createUrlNode(baseUrl + "/documents/" + document.getNamedId(), "yearly"))));
        articleRepository.findAll().forEach(article -> urlSetNode.appendChild(createUrlNode(baseUrl + "/article/" + article.getNamedId(), "monthly")));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/kahetyzm-ugcc", "yearly"));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/kahehyzm", "yearly"));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/gallery", "weekly"));
        albumRepository.findAll().forEach(album -> urlSetNode.appendChild(createUrlNode(baseUrl + "/albums/" + album.getNamedId(), "never")));
        urlSetNode.appendChild(createUrlNode(baseUrl + "/contacts", "yearly"));

    }

    private String getXmlToString() {
        StringWriter writer = new StringWriter();
        String sitemapXml = "";

        try {
            transformer.transform(new DOMSource(sitemap), new StreamResult(writer));
            sitemapXml = writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return sitemapXml;
    }
}

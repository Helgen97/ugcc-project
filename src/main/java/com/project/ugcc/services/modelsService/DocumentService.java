package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.DocumentDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Document;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.DocumentRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService implements TypeService<Document> {

    private final static Logger LOGGER = LogManager.getLogger(DocumentService.class.getName());

    private final DocumentRepository documentRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, SectionRepository sectionRepository) {
        this.documentRepository = documentRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Document> getOneById(Long id) {
        LOGGER.info(String.format("Getting document by id: %d", id));
        return documentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Document> getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting document by named id. Named id: %s", namedId));
        return documentRepository.findByNamedId(namedId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> getAll() {
        LOGGER.info("Getting all documents");
        return documentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<DocumentDTO> getPageOfDocuments(int page, int size) {
        LOGGER.info(String.format("Get page of documents. Page: %d. Size: %d", page, size));
        Page<Document> documentPage = documentRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "ID")));
        return documentPage.map(DocumentDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<DocumentDTO> getPageOfDocumentsBySectionId(Long id, int page, int size) {
        LOGGER.info(String.format("Get page of documents by section id. Section id: %d. Page: %d. Size: %d", id, page, size));
        Page<Document> documentPage = documentRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return documentPage.map(DocumentDTO::of);
    }

    @Override
    @Transactional
    public Document create(Document document) {
        LOGGER.info("Creating new document");
        document.setNamedId(UrlConverter.urlTransliterate(document.getTitle()));
        document.setCreationDate(LocalDateTime.now());
        return documentRepository.save(document);
    }

    @Override
    @Transactional(readOnly = true)
    public Document setSectionToModel(Document document, Long sectionId) {
        LOGGER.info(String.format("Setting section to new document. Section id: %d", sectionId));
        Section section = sectionRepository.findByID(sectionId).orElseThrow(() -> new NotFoundException("Section with this ID not found"));
        document.setSection(section);
        return document;
    }

    @Override
    @Transactional
    public Document update(Document document) {
        LOGGER.info(String.format("Updating document with id: %d", document.getID()));
        return documentRepository.save(document);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting document with id: %d", id));
        documentRepository.deleteById(id);
    }
}

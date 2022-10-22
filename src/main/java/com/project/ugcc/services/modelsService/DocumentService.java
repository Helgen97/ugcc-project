package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.DocumentDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Document;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.DocumentRepository;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService implements TypeService<Document> {

    private final static Logger LOGGER = LogManager.getLogger(DocumentService.class.getName());

    private final DocumentRepository documentRepository;
    private final SectionRepository sectionRepository;
    private final FileStorageService fileService;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, SectionRepository sectionRepository, FileStorageService fileService) {
        this.documentRepository = documentRepository;
        this.sectionRepository = sectionRepository;
        this.fileService = fileService;
    }

    @Override
    @Transactional(readOnly = true)
    public Document getOneById(Long id) {
        LOGGER.info(String.format("Getting document by id. Document Id: %d", id));
        return documentRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Document with id %s not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Document getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting document by named id. Named id: %s", namedId));
        return documentRepository.findByNamedId(namedId).orElseThrow(() -> new NotFoundException(String.format("Document with named id: %s not found", namedId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> getAll() {
        LOGGER.info("Getting all documents");
        return documentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DocumentDTO> getFourRandomDocumentsExceptArticleWithId(Long id) {
        List<Document> documentList = documentRepository.findAll().stream().filter(document -> !document.getId().equals(id)).collect(Collectors.toList());
        Collections.shuffle(documentList);
        return documentList.stream().limit(4).map(DocumentDTO::of).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<DocumentDTO> getPageOfDocuments(int page, int size) {
        LOGGER.info(String.format("Get page of documents. Page: %d. Size: %d", page, size));
        Page<Document> documentPage = documentRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return documentPage.map(DocumentDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<DocumentDTO> getPageOfDocumentsBySectionId(Long id, int page, int size) {
        LOGGER.info(String.format("Get page of documents by section id. Section id: %d. Page: %d. Size: %d", id, page, size));
        Page<Document> documentPage = documentRepository.findAllBySectionId(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        return documentPage.map(DocumentDTO::of);
    }

    @Override
    @Transactional
    public Document create(Document document) {
        LOGGER.info("Creating new document");
        document.setNamedId(Utils.transliterateStringFromCyrillicToLatinChars(document.getTitle()));
        document.setCreationDate(Utils.convertDateToUkrainianDateString(LocalDateTime.now()));
        return documentRepository.save(document);
    }

    @Override
    @Transactional(readOnly = true)
    public Document setSectionToModel(Document document, Long sectionId) {
        LOGGER.info(String.format("Setting section to new document. Section id: %d", sectionId));
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new NotFoundException("Section with this ID not found"));
        document.setSection(section);
        return document;
    }

    @Override
    @Transactional
    public Document update(Document document) {
        LOGGER.info(String.format("Updating document. Document id: %d", document.getId()));

        Document documentToUpdate = documentRepository.findById(document.getId()).orElseThrow(() -> new NotFoundException(String.format("Document with id: %s not found!", document.getId())));

        if (!documentToUpdate.getImageUrl().equals(document.getImageUrl())) {
            fileService.deleteFile(documentToUpdate.getImageUrl());
        }
        if (!documentToUpdate.getDocumentUrl().equals(document.getDocumentUrl())) {
            fileService.deleteFile(documentToUpdate.getDocumentUrl());
        }

        return documentRepository.save(document);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOGGER.info(String.format("Deleting document. Document id: %d", id));

        Document documentToDelete = documentRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("News with id: %s not found!", id)));
        fileService.deleteFile(documentToDelete.getImageUrl());
        fileService.deleteFile(documentToDelete.getDocumentUrl());

        documentRepository.delete(documentToDelete);
    }
}

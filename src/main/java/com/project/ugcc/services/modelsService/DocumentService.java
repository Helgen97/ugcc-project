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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentService implements TypeService<Document, DocumentDTO> {

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
    public DocumentDTO getOneById(Long id) {
        LOGGER.info(String.format("Getting document by id. Document Id: %d", id));
        return DocumentDTO.of(documentRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Document with id %s not found", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentDTO getByNamedId(String namedId) {
        LOGGER.info(String.format("Getting document by named id. Named id: %s", namedId));
        return DocumentDTO.of(documentRepository.findByNamedId(namedId).orElseThrow(() -> new NotFoundException(String.format("Document with named id: %s not found", namedId))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentDTO> getAll() {
        LOGGER.info("Getting all documents");
        return documentRepository.findAll().stream().map(DocumentDTO::of).collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, List<DocumentDTO>> getAllDocumentsFilteredBySection() {
        Map<String, List<DocumentDTO>> sortedDocuments = new HashMap<>();

        List<DocumentDTO> documentsOfExarchate = new ArrayList<>();
        List<DocumentDTO> documentsOfChurch = new ArrayList<>();

        documentRepository.findAll().parallelStream()
                .map(DocumentDTO::of)
                .forEach(documentDTO -> {
                    if (documentDTO.getSection().getId().equals(4L)) {
                        documentsOfExarchate.add(documentDTO);
                    } else {
                        documentsOfChurch.add(documentDTO);
                    }
                });

        sortedDocuments.put("documentsOfExarchate", documentsOfExarchate);
        sortedDocuments.put("documentsOfChurch", documentsOfChurch);

        return sortedDocuments;
    }

    @Transactional(readOnly = true)
    public List<DocumentDTO> getListWithMainDocumentAndFourRandomDocuments(String namedId) {
        List<DocumentDTO> documentList = documentRepository.findAll().stream().
                map(DocumentDTO::of)
                .collect(Collectors.toList());

        List<DocumentDTO> neededDocuments = new ArrayList<>();



        return neededDocuments;
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
    public DocumentDTO create(Document document) {
        LOGGER.info("Creating new document");
        document.setNamedId(Utils.transliterateStringFromCyrillicToLatinChars(document.getTitle()));
        document.setCreationDate(Utils.convertDateToUkrainianDateString(LocalDateTime.now()));
        return DocumentDTO.of(documentRepository.save(document));
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
    public DocumentDTO update(Document document) {
        LOGGER.info(String.format("Updating document. Document id: %d", document.getId()));

        Document documentToUpdate = documentRepository.findById(document.getId()).orElseThrow(() -> new NotFoundException(String.format("Document with id: %s not found!", document.getId())));

        if (!documentToUpdate.getImageUrl().equals(document.getImageUrl())) {
            fileService.deleteFile(documentToUpdate.getImageUrl());
        }
        if (!documentToUpdate.getDocumentUrl().equals(document.getDocumentUrl())) {
            fileService.deleteFile(documentToUpdate.getDocumentUrl());
        }

        return DocumentDTO.of(documentRepository.save(document));
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

package com.project.ugcc.services.modelsService;

import com.project.ugcc.DTO.DocumentDTO;
import com.project.ugcc.exceptions.NotFoundException;
import com.project.ugcc.models.Document;
import com.project.ugcc.models.Section;
import com.project.ugcc.repositories.DocumentRepository;
import com.project.ugcc.repositories.SectionRepository;
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
        return documentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<DocumentDTO> getPageOfDocuments(int page, int size) {
        Page<Document> documentPage = documentRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "ID")));
        return documentPage.map(DocumentDTO::of);
    }

    @Transactional(readOnly = true)
    public Page<DocumentDTO> getPageOfDocumentsBySectionId(Long id, int page, int size) {
        Page<Document> documentPage = documentRepository.findAllBySectionID(
                id,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ID")));
        return documentPage.map(DocumentDTO::of);
    }

    @Override
    @Transactional
    public Document create(Document document) {
        document.setCreationDate(LocalDateTime.now());
        return documentRepository.save(document);
    }

    @Override
    @Transactional(readOnly = true)
    public Document setSectionToModel(Document document, Long sectionId) {
        Section section = sectionRepository.findByID(sectionId).orElseThrow(() -> new NotFoundException("Section with this ID not found"));
        document.setSection(section);
        return document;
    }

    @Override
    @Transactional
    public Document update(Document document) {
        return documentRepository.save(document);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }
}

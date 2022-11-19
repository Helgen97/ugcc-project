package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.DocumentDTO;
import com.project.ugcc.models.Document;
import com.project.ugcc.services.modelsService.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentDTO> getAll() {
        return documentService.getAll();
    }

    @GetMapping("/{id}")
    public DocumentDTO getOne(@PathVariable Long id) {
        return documentService.getOneById(id);
    }

    @GetMapping("/pages/random")
    public Page<DocumentDTO> getFourRandomDocuments() {
        return documentService.getFourRandomDocuments();
    }

    @GetMapping("/pages")
    public Page<DocumentDTO> getPageOfDocuments(@RequestParam(required = false) Long sectionId,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        return sectionId == null
                ?
                documentService.getPageOfDocuments(page, size)
                :
                documentService.getPageOfDocumentsBySectionId(sectionId, page, size);
    }

    @PostMapping
    public DocumentDTO create(@RequestBody Document document, @RequestParam Long sectionId) {
        Document documentWithSection = documentService.setSectionToModel(document, sectionId);
        return documentService.create(documentWithSection);
    }

    @PutMapping
    public DocumentDTO update(@RequestBody Document document) {
        return documentService.update(document);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        documentService.delete(id);
    }
}

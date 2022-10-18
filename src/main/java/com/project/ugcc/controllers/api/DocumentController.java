package com.project.ugcc.controllers.api;

import com.project.ugcc.DTO.DocumentDTO;
import com.project.ugcc.models.Document;
import com.project.ugcc.services.modelsService.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return documentService.getAll().stream().map(DocumentDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DocumentDTO getOne(@PathVariable Long id) {
        return DocumentDTO.of(documentService.getOneById(id));
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
        return DocumentDTO.of(documentService.create(documentWithSection));
    }

    @PutMapping
    public DocumentDTO update(@RequestBody Document document) {
        return DocumentDTO.of(documentService.update(document));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        documentService.delete(id);
    }
}

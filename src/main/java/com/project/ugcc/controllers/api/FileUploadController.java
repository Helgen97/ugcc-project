package com.project.ugcc.controllers.api;

import com.project.ugcc.services.fileService.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final FileStorageService fileService;

    @Autowired
    public FileUploadController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String folder) {
        fileService.saveFile(file, folder);

        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return String.format("%s/api/upload?filename=%s&folder=%s&date=%s",
                baseUrl, file.getOriginalFilename(),
                folder,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @GetMapping
    public ResponseEntity<Resource> getUploadedFile(@RequestParam String filename, @RequestParam String folder, @RequestParam String date) {

        Resource file = fileService.loadAsResource(filename, folder, date);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", file.getFilename())).body(file);
    }
}

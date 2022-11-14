package com.project.ugcc.controllers.api;

import com.project.ugcc.services.fileService.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final FileStorageService fileService;

    @Autowired
    public FileUploadController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/api/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String filePath = fileService.saveFile(file);
        return "/public/" + filePath;
    }

    @GetMapping("/public/{folder}/{fileName}")
    public ResponseEntity<Resource> getUploadedFile(@PathVariable String folder, @PathVariable String fileName) {

        Resource file = fileService.loadAsResource(folder, fileName);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", file.getFilename())).body(file);
    }
}

package com.project.ugcc.controllers.api;

import com.project.ugcc.services.fileService.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final FileStorageService fileService;

    @Autowired
    public FileUploadController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam String collectionName,
                             @RequestParam String collectionItemTitle) {
        String filePath = fileService.saveFile(file, collectionName, collectionItemTitle);

        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return baseUrl + "/api/upload/" + filePath;
    }

    @GetMapping("/{folder}/{collectionFolderName}/{date}/{collectionItemTitle}/{filename}")
    public ResponseEntity<Resource> getUploadedFile(@PathVariable String folder,
                                                    @PathVariable String collectionFolderName,
                                                    @PathVariable String date,
                                                    @PathVariable String collectionItemTitle,
                                                    @PathVariable String filename
    ) {

        Resource file = fileService.loadAsResource(folder, collectionFolderName, date, collectionItemTitle, filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", file.getFilename())).body(file);
    }
}

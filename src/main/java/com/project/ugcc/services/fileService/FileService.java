package com.project.ugcc.services.fileService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {

    void init();

    void saveFile(MultipartFile file, String folder);

    Path load(String filename);

    Resource loadAsResource(String filename, String folder);

    void deleteAll();

}

package com.project.ugcc.services.fileService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {

    void init();

    void createDirectory(String path);

    public boolean isDirectoryEmpty(Path path);

    String saveFile(MultipartFile file, String collectionsNameFolder, String collectionsItemTitleFolder);

    Path load(String filePath);

    Resource loadAsResource(String folder, String collectionsFolderName, String date, String collectionsItemTitle, String filename);

    String getPathFromFileUrl(String fileUrl);

    void deleteFile(String fileUrl);

    void deleteAll();

}

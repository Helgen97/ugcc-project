package com.project.ugcc.services.fileService;

import com.project.ugcc.exceptions.StorageException;
import com.project.ugcc.exceptions.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements FileService {

    private final Path location = Paths.get("upload");
    private final Path imagesLocation = Paths.get("upload/images");
    private final Path documentsLocation = Paths.get("upload/documents");

    @Override
    public void init() {
        try {

            Files.createDirectories(location);
            Files.createDirectories(imagesLocation);
            Files.createDirectories(documentsLocation);

        } catch (IOException exception) {
            throw new StorageException("Could not initialize storage", exception);
        }
    }

    @Override
    public void saveFile(MultipartFile file, String folder) {
        if (file.isEmpty()) throw new StorageException("Could not save empty file");

        try {
            Path destinationFile = location.resolve(folder + File.separator +
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().toString().equals(location.toAbsolutePath() + File.separator + folder)) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            throw new StorageException("Failed to store file.", ex);
        }
    }

    @Override
    public Path load(String filename) {
        return location.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename, String folder) {
        try {
            Path file = load(String.format("%s/%s", folder, filename));
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(location.toFile());
    }
}

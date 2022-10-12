package com.project.ugcc.services.fileService;

import com.project.ugcc.exceptions.StorageException;
import com.project.ugcc.exceptions.StorageFileNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileStorageService implements FileService {

    private static final Logger LOGGER = LogManager.getLogger(FileStorageService.class.getName());

    private final Path baseLocation = Paths.get("uploads");

    @Override
    public void init() {
        LOGGER.info("Initializing base upload directory.");
        try {
            Files.createDirectories(baseLocation);
            LOGGER.info("Initializing base upload directory - success.");
        } catch (IOException exception) {
            LOGGER.error("Initializing base upload directory - error.", exception);
            throw new StorageException("Could not initialize storage", exception);
        }
    }

    @Override
    public void createDirectory(String path) {
        LOGGER.info("Initializing file upload directory.");
        try {
            Files.createDirectories(Paths.get(path));
            LOGGER.info("Initializing file upload directory - success.");
        } catch (IOException exception) {
            LOGGER.info("Initializing file upload directory - error.", exception);
            throw new StorageException("Could not initialize storage", exception);
        }
    }

    @Override
    public void saveFile(MultipartFile file, String folder) {
        if (file.isEmpty()) {
            LOGGER.error("Could not save empty file.");
            throw new StorageException("Could not save empty file");
        }

        LOGGER.info("Saving uploaded file.");
        String destinationDirectory = baseLocation.toAbsolutePath() + File.separator + folder + File.separator + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String destinationFilePath = String.format("%s%s%s%s%s",
                folder,
                File.separator,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                File.separator, Paths.get(file.getOriginalFilename()));

        createDirectory(destinationDirectory);

        try {
            Path destinationFile = baseLocation.resolve(destinationFilePath).normalize().toAbsolutePath();

            if (!destinationFile.getParent().toString().equals(destinationDirectory)) {
                LOGGER.error("Cannot store file outside current directory.");
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            LOGGER.error("Failed to store file.", ex);
            throw new StorageException("Failed to store file.", ex);
        }
    }

    @Override
    public Path load(String filename) {
        return baseLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename, String folder, String date) {
        try {
            LOGGER.info("Trying get uploaded file");
            Path file = load(String.format("%s%s%s%s%s", folder, File.separator, date, File.separator, filename));
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            LOGGER.error("Could not read file: " + filename, e);
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(baseLocation.toFile());
    }
}

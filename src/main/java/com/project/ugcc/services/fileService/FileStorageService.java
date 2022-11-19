package com.project.ugcc.services.fileService;

import com.project.ugcc.exceptions.StorageException;
import com.project.ugcc.exceptions.StorageFileNotFoundException;
import com.project.ugcc.utils.Utils;
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
import java.util.Objects;

@Service
public class FileStorageService implements FileService {

    private static final Logger LOGGER = LogManager.getLogger(FileStorageService.class.getName());

    private final Path baseLocation = Paths.get("uploads");

    @Override
    public void init() {
        LOGGER.info("Initializing base upload directory.");
        try {
            Files.createDirectories(baseLocation);
            Files.createDirectories(Paths.get(baseLocation + File.separator + "images"));
            Files.createDirectories(Paths.get(baseLocation + File.separator + "file"));
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

    private String getFolderBasedOnFileContentType(String fileContentType) {
        return fileContentType.startsWith("image/") ? "images" : "file";
    }

    private String generateRandomFileName(String fileOriginalName) {
        return Utils.generateRandomName() + fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
    }

    @Override
    public String saveFile(MultipartFile file) {
        LOGGER.info("Saving uploaded file.");

        if (file.isEmpty()) {
            LOGGER.error("Could not save empty file.");
            throw new StorageException("Could not save empty file");
        }


        try {
            Path destinationFile = baseLocation.resolve(getFolderBasedOnFileContentType(Objects.requireNonNull(file.getContentType())) + File.separator
                                                        + Paths.get(generateRandomFileName(Objects.requireNonNull(file.getOriginalFilename()))))
                    .normalize().toAbsolutePath();


            if (!destinationFile.getParent().toString().equals(baseLocation.toAbsolutePath() + File.separator + getFolderBasedOnFileContentType(file.getContentType()))) {
                LOGGER.error("Cannot store file outside current directory.");
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return getPathOfUploadedFile(destinationFile);
            }

        } catch (IOException ex) {
            LOGGER.error("Failed to store file.", ex);
            throw new StorageException("Failed to store file.", ex);
        }
    }

    private String getPathOfUploadedFile(Path destinationFile) {
        return destinationFile.toString().replace(baseLocation.toAbsolutePath() + File.separator, "");
    }

    @Override
    public Path load(String filePath) {
        return baseLocation.resolve(filePath);
    }

    @Override
    public Resource loadAsResource(String folder, String filename) {
        String filePath = folder + File.separator + filename;
        try {
            LOGGER.info("Trying get uploaded file");
            Path file = load(filePath);
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
    public String getPathFromFileUrl(String fileUrl) {
        return baseLocation + fileUrl.substring(fileUrl.indexOf("public") + "public".length());
    }

    @Override
    public void deleteFile(String fileUrl) {
        String pathToFile = getPathFromFileUrl(fileUrl);

        LOGGER.info(String.format("Deleting file: %s", pathToFile));
        Path path = Paths.get(pathToFile);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            LOGGER.error("IOException while deleting file", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(baseLocation.toFile());
    }
}

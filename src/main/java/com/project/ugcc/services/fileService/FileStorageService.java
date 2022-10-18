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
import java.nio.file.*;
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
    public boolean isDirectoryEmpty(Path path) {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            } catch (IOException ex) {
                LOGGER.error("Checking if directory empty error", ex);
            }
        }

        return false;
    }

    private String getFolderBasedOnFileContentType(String fileContentType) {
        return fileContentType.startsWith("image/") ? "images" : "documents";
    }

    private String createFileDestinationDirectory(String fileContentType, String collectionNameFolder, String collectionItemTitleFolder) {
        String destinationDirectory = baseLocation.toAbsolutePath()
                + File.separator
                + getFolderBasedOnFileContentType(fileContentType)
                + File.separator
                + collectionNameFolder
                + File.separator
                + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + File.separator
                + collectionItemTitleFolder;
        createDirectory(destinationDirectory);
        return destinationDirectory;
    }

    private String createFileDestinationPath(String fileDestinationDirectory, String fileOriginalName) {
        return fileDestinationDirectory.replace(baseLocation.toAbsolutePath() + File.separator, "")
                + File.separator
                + fileOriginalName;
    }

    @Override
    public String saveFile(MultipartFile file, String collectionNameFolder, String collectionItemTitleFolder) {
        LOGGER.info("Saving uploaded file.");
        if (file.isEmpty()) {
            LOGGER.error("Could not save empty file.");
            throw new StorageException("Could not save empty file");
        }

        String destinationDirectory = createFileDestinationDirectory(file.getContentType(), collectionNameFolder, Utils.transliterateStringFromCyrillicToLatinChars(collectionItemTitleFolder));
        String destinationFilePath = createFileDestinationPath(destinationDirectory, file.getOriginalFilename());

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
                return destinationFilePath;
            }

        } catch (IOException ex) {
            LOGGER.error("Failed to store file.", ex);
            throw new StorageException("Failed to store file.", ex);
        }
    }

    @Override
    public Path load(String filePath) {
        return baseLocation.resolve(filePath);
    }

    @Override
    public Resource loadAsResource(String folder, String collectionFolderName, String date, String collectionItemTitle, String filename) {
        String filePath = folder
                + File.separator
                + collectionFolderName
                + File.separator
                + date
                + File.separator
                + collectionItemTitle
                + File.separator + filename;
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
        return baseLocation + fileUrl.substring(fileUrl.indexOf("upload") + "upload".length());
    }

    @Override
    public void deleteFile(String fileUrl) {
        String pathToFile = getPathFromFileUrl(fileUrl);

        LOGGER.info(String.format("Deleting file: %s", pathToFile));
        Path path = Paths.get(pathToFile);

        try {
            Files.deleteIfExists(path);

            if (isDirectoryEmpty(path.getParent())) {
                FileSystemUtils.deleteRecursively(path.getParent());
            }
        } catch (IOException e) {
            LOGGER.error("IOException while deleting file", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(baseLocation.toFile());
    }
}

package pl.edu.pg.eti.kask.store.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PhotoService {
    private final Path photoDirectory;

    public PhotoService(String photoDirectory) {
        this.photoDirectory = Paths.get(photoDirectory);
        try {
            Files.createDirectories(this.photoDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Could not create photo directory", e);
        }
    }

    public String savePhoto(UUID photoOwnerId, InputStream photoInputStream) throws IOException {
        String uniqueFileName = createUniqueFileName(photoOwnerId);
        Path photoPath = this.photoDirectory.resolve(uniqueFileName);
        Files.copy(photoInputStream, photoPath);
        return uniqueFileName;
    }

    public InputStream getPhoto(String fileName) throws IOException {
        Path photoPath = this.photoDirectory.resolve(fileName);
        if (Files.exists(photoPath)) {
            return Files.newInputStream(photoPath);
        } else {
            throw new IOException("Photo not found");
        }
    }

    public void deletePhoto(String fileName) throws IOException {
        Path photoPath = this.photoDirectory.resolve(fileName);
        if (Files.exists(photoPath)) {
            Files.delete(photoPath);
        } else {
            throw new IOException("Photo not found");
        }
    }

    private String createUniqueFileName(UUID photoOwnerId) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        return photoOwnerId.toString() + "-" + timestamp + ".jpg";
    }
}

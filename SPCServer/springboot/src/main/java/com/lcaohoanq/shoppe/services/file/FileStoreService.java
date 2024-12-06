package com.lcaohoanq.shoppe.services.file;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.exceptions.FileTooLargeException;
import com.lcaohoanq.shoppe.exceptions.UnsupportedMediaTypeException;
import com.lcaohoanq.shoppe.models.ProductImage;
import com.lcaohoanq.shoppe.utils.MessageKey;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStoreService implements IFileStoreService {

    private final LocalizationUtils localizationUtils;

    public FileStoreService(LocalizationUtils localizationUtils) {
        this.localizationUtils = localizationUtils;
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        //File.separator: depends on the OS, for windows it is '\', for linux it is '/'
        Path destination = Paths.get(uploadDir + File.separator + uniqueFileName);
        //copy the file to the destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @Override
    public List<MultipartFile> validateListProductImage(List<MultipartFile> files) throws IOException {
        files = files == null ? new ArrayList<>() : files;
        if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new IOException(localizationUtils.getLocalizedMessage(MessageKey.UPLOAD_IMAGES_MAX_5));
        }
        return files;
    }

    @Override
    public MultipartFile validateProductImage(MultipartFile file) throws IOException {
        // Kiểm tra kích thước file và định dạng
        if (file.getSize() == 0) {
            throw new IOException("File is empty");
        }

        if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
            throw new FileTooLargeException("File is too large");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new UnsupportedMediaTypeException("Unsupported media type");
        }
        return file;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

}

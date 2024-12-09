package com.lcaohoanq.shoppe.domain.asset;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStoreService {

    String storeFile(MultipartFile file) throws IOException;

    List<MultipartFile> validateListProductImage(List<MultipartFile> files) throws IOException;

    MultipartFile validateProductImage(MultipartFile file) throws IOException;
}

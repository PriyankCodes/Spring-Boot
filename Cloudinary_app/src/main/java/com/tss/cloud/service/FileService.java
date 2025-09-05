package com.tss.cloud.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tss.cloud.dto.FileResponse;
import com.tss.cloud.entity.FileEntity;
import com.tss.cloud.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Cloudinary cloudinary;
    private final FileRepository fileRepository;

    public FileResponse uploadFile(MultipartFile file) throws IOException {
        // Upload to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        String url = (String) uploadResult.get("secure_url");

        // Save metadata to DB
        FileEntity entity = FileEntity.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileUrl(url)
                .build();

        fileRepository.save(entity);
        return new FileResponse("File uploaded successfully!", true, url);
    }

    public FileEntity getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }
}

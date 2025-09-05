package com.tss.mail.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tss.mail.entity.ImageEntity;
import com.tss.mail.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);
        return "Image uploaded successfully: " + image.getId();
    }

    @Override
    public ImageEntity getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id " + id));
    }
}

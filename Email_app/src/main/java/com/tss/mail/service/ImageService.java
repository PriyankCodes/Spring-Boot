package com.tss.mail.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.tss.mail.entity.ImageEntity;

public interface ImageService {

	ImageEntity getImage(Long id);

	String uploadImage(MultipartFile file) throws IOException;
}

package com.tss.cloud.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tss.cloud.entity.FileEntity;
import com.tss.cloud.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final Cloudinary cloudinary;
	private final FileRepository fileRepository;

	public FileEntity uploadFile(MultipartFile file) throws IOException {

		Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

		String url = (String) uploadResult.get("secure_url");

		FileEntity entity = FileEntity.builder().fileName(file.getOriginalFilename()).fileType(file.getContentType())
				.fileUrl(url).build();

		return fileRepository.save(entity);
	}

	public FileEntity getFile(Long id) {
		return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id " + id));
	}
}

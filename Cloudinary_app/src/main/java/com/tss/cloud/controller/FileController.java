package com.tss.cloud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tss.cloud.entity.FileEntity;
import com.tss.cloud.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		return ResponseEntity.ok(fileService.uploadFile(file));
	}

	@GetMapping("/{id}")
	public ResponseEntity<FileEntity> getFile(@PathVariable Long id) {
		return ResponseEntity.ok(fileService.getFile(id));
	}
}

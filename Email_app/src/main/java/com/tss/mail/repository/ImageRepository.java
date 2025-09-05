package com.tss.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tss.mail.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}

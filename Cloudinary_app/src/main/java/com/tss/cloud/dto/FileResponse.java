package com.tss.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {
    private String message;
    private boolean status;
    private String fileUrl;
}

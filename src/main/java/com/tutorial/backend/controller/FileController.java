package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.ResultDto;
import com.tutorial.backend.entity.File;
import com.tutorial.backend.service.file.FileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file/*")
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResultDto<Long>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File uploadedFile = fileService.uploadFile(file);
            return ResponseEntity.ok().body(ResultDto.res(HttpStatus.ACCEPTED, "Uploading File success", uploadedFile.getId()));
        } catch (IOException e) {
            log.error("Error uploading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultDto.res(HttpStatus.INTERNAL_SERVER_ERROR,"Something been through Wrong..."));
        }
    }

    @GetMapping("files/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        File file = fileService.getFileByOriginName(fileName);
        if (file == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }

        try {
            Path filePath = Paths.get(file.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error downloading file");
        }
    }
}

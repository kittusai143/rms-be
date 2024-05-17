package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("/resumes")
public class ResumeController {
    @Autowired
    private GoogleDriveService googleDriveService;
    @GetMapping("/search")
    public ResponseEntity<String> searchFile(@RequestParam String fileName) {
        try {
            String fileLink = googleDriveService.getFileLink(fileName);
            if (fileLink != null) {
                return ResponseEntity.ok(fileLink);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while searching for the file: " + e.getMessage());
        }
    }
}

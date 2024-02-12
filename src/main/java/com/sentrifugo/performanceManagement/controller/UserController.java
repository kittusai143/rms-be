package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.repository.EmailAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private EmailAuthenticationRepo customRepository;

    @GetMapping("/managers")
    public ResponseEntity<?> getManagers(@RequestParam Integer Id) {
        // Retrieve reporting manager name
        List<String> reportingManagerNames = customRepository.getReportingManagerNames(Id);

        if (reportingManagerNames.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format");
        else
            return ResponseEntity.ok(reportingManagerNames);

    }
    @GetMapping("/l2managers")
    public ResponseEntity<?> getL2Managers(@RequestParam Integer Id) {
        // Retrieve reporting manager name
        List<String> reportingManagerNames = customRepository.getL2ManagerNames(Id);

        if (reportingManagerNames.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format");
        else
            return ResponseEntity.ok(reportingManagerNames);
    }
}

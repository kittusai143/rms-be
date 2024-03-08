package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.service.SelfAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/sa")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class SelfAssessmentController {
    @Autowired
    SelfAssessmentService selfAssessmentService;
       @Autowired
       EmployeeRepo ERepo;
    @GetMapping("/am-status/{eid}")
    public ResponseEntity<?> getamStatus(@PathVariable Integer eid) {
        try {
          Integer employeeId=  ERepo.getEmpId(eid);
            AppraisalMaster appraisalMaster = selfAssessmentService.getamStatus(employeeId);

            if (appraisalMaster != null) {
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("message", "success");
                successResponse.put("appraisalMaster", appraisalMaster);
                return ResponseEntity.ok(successResponse);
            } else {
                Map<String, Object> notFoundResponse = new HashMap<>();
                notFoundResponse.put("message", "no active record found");
                return ResponseEntity.ok(notFoundResponse);
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "error");
            errorResponse.put("error_message", "error retrieving data for id: " + eid);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/activemId/{eid}")
    public ResponseEntity<?> getActivemid(@PathVariable Long eid) {
        try {
            Long activeMid = selfAssessmentService.getActiveAppraisalMasterId(eid);

            if (activeMid != null) {
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("message", "success");
                successResponse.put("activeMid", activeMid);
                return ResponseEntity.ok(successResponse);
            } else {
                Map<String, Object> notFoundResponse = new HashMap<>();
                notFoundResponse.put("message", "notFound");
                return ResponseEntity.ok(notFoundResponse);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "error");
            errorResponse.put("error_message", "An unexpected error occurred while processing the request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    @PutMapping("/status/{mid}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Integer mid, @PathVariable String status) {
        try {
            Map<String, String> op = selfAssessmentService.changeStatus(mid, status);
            return ResponseEntity.ok(op);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "An unexpected error occurred while processing the request");

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/get-all-rows/{mid}")
    public ResponseEntity<?> getSelfAssessmentFormById(@PathVariable Integer mid) {
        try {
            List<SelfAssessment> selfAssessmentForm = selfAssessmentService.getSelfAssessmentFormByMasterId(mid);

            if (!selfAssessmentForm.isEmpty()) {
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("message", "success");
                successResponse.put("form", selfAssessmentForm);
                return ResponseEntity.ok(successResponse);
            } else {
                Map<String, Object> notFoundResponse = new HashMap<>();
                notFoundResponse.put("message", "no records found");
                return ResponseEntity.ok(notFoundResponse);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "error");
            errorResponse.put("error_message", "An unexpected error occurred while processing the request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<List<SelfAssessment>> submitSelfAssessmentForm(@RequestBody List<SelfAssessment> assessments) {
        System.out.println(assessments);
        List<SelfAssessment> submittedAssessments = selfAssessmentService.submit(assessments);
        return new ResponseEntity<>(submittedAssessments, HttpStatus.CREATED);
    }

    @PostMapping("/add-goal/{question}")
    public ResponseEntity<SelfAssessment> addQuestion(@PathVariable String question) {
        try {
            SelfAssessment newAssessment = new SelfAssessment();
            newAssessment.setQuestion(question);
            selfAssessmentService.addRow(newAssessment);
            return new ResponseEntity<>(newAssessment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/getbyId/{id}")
    public ResponseEntity<SelfAssessment> getSelfAssessmentById(@PathVariable Integer id) {
        SelfAssessment selfAssessment = selfAssessmentService.getSelfAssessmentById(id);

        if (selfAssessment != null) {
            return new ResponseEntity<>(selfAssessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteSelfAssessmentById(@PathVariable Integer id) {
        boolean deleted = selfAssessmentService.deleteSelfAssessmentById(id);

        if (deleted) {
            return new ResponseEntity<>("Question with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String uploadResult = selfAssessmentService.uploadFile(file);
            Map<String, Object> response = new HashMap<>();
            if(uploadResult.equals("empty")){
                response.put("message", "empty-file");
                return ResponseEntity.ok(response);
            }
            else if(uploadResult.equals("max__size")){
                response.put("message", "sizeErr");
                return ResponseEntity.ok(response);
            }
            response.put("message", "success");
            response.put("filepath", uploadResult);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "error");
            errorResponse.put("error_message", "An unexpected error occurred during file upload");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    private static final String BASE_PATH = "C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\attachments\\";

    @DeleteMapping("/deleteFile/{filename}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(BASE_PATH + filename);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                Map<String, String> successResponse = new HashMap<>();
                successResponse.put("message", "success");
                successResponse.put("result", "File deleted successfully");
                return ResponseEntity.ok(successResponse);
            } else {
                Map<String, String> notFoundResponse = new HashMap<>();
                notFoundResponse.put("message", "error");
                notFoundResponse.put("result", "File not found");
                return ResponseEntity.ok(notFoundResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "error");
            errorResponse.put("result", "An unexpected error occurred while deleting the file");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String fileName) {
        FileSystemResource fileResource = selfAssessmentService.getFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResource.getFile().length())
                .body(fileResource);
    }

    @PostMapping("/initialize/{masterId}")
    public ResponseEntity<?> initializeAssessment(@PathVariable Long masterId) {
        try {
            Map<String, String> resp = new HashMap<>();
            selfAssessmentService.submitWithDefaultQuestions(masterId);
            resp.put("message", "initialized");
            resp.put("result", "Assessment initialized successfully");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "failed");
            resp.put("result", "Failed to initialize assessment");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    ///-------trash

    @GetMapping("/getbymid/{mid}")
    public ResponseEntity<List<SelfAssessment>> getSelfAssessmentsByMid(@PathVariable Integer mid) {
        List<SelfAssessment> selfAssessments = selfAssessmentService.getSelfAssessmentbymid(mid);

        if (!selfAssessments.isEmpty()) {
            return new ResponseEntity<>(selfAssessments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SelfAssessment> updateSelfAssessment(@PathVariable Integer id, @RequestBody SelfAssessment updatedAssessment) {
        SelfAssessment updatedAssessmentResult = selfAssessmentService.updateSelfAssessment(id, updatedAssessment);
        return new ResponseEntity<>(updatedAssessmentResult, HttpStatus.OK);
    }

    @GetMapping("/status/{eid}")
    public ResponseEntity<?> getStatus(@PathVariable Integer eid){

        return ResponseEntity.ok(selfAssessmentService.getStatus(eid));
    }

    @GetMapping("/get-all-rows")
    public ResponseEntity<List<SelfAssessment>> getSelfAssessmentForm() {
        try {
            List<SelfAssessment> selfAssessmentForm = selfAssessmentService.getSelfAssessmentForm();
            return new ResponseEntity<>(selfAssessmentForm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

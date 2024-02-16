package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.repository.SelfAssessmentRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SelfAssessmentService {
    @Autowired
    private SelfAssessmentRepository selfAssessmentRepository;
    @Autowired
    private AppraisalMasterRepository aprepo;


     public List<SelfAssessment> getSelfAssessmentForm() {
        return selfAssessmentRepository.findAll();
    }

    public SelfAssessment getSelfAssessmentById(Integer id) {
        return selfAssessmentRepository.findById(id).orElse(null);
    }

    public List<SelfAssessment> getSelfAssessmentbymid(Integer mid){
        return selfAssessmentRepository.findByAppraisalMasterId(mid);
    }

    public SelfAssessment addRow(SelfAssessment newAssessment) {
       newAssessment.setAppraisalMasterId(2);
        return selfAssessmentRepository.save(newAssessment);
    }

       public SelfAssessment updateSelfAssessment(Integer id, SelfAssessment updatedAssessment) {
        Optional<SelfAssessment> existingAssessmentOptional = selfAssessmentRepository.findById(id);

        if (existingAssessmentOptional.isPresent()) {
            SelfAssessment existingAssessment = existingAssessmentOptional.get();

            // Update fields only if they are not null in the updatedAssessment
            if (updatedAssessment.getQuestion() != null) {
                existingAssessment.setQuestion(updatedAssessment.getQuestion());
            }
            if (updatedAssessment.getStatus() != null) {
                existingAssessment.setStatus(updatedAssessment.getStatus());
            }
            if (updatedAssessment.getManagerComments() != null) {
                existingAssessment.setManagerComments(updatedAssessment.getManagerComments());
            }
            if (updatedAssessment.getManagerRating() != null) {
                existingAssessment.setManagerRating(updatedAssessment.getManagerRating());
            }
            if (updatedAssessment.getEmployeeRating() != null) {
                existingAssessment.setEmployeeRating(updatedAssessment.getEmployeeRating());
            }
            if (updatedAssessment.getEmployeeComments() != null) {
                existingAssessment.setEmployeeComments(updatedAssessment.getEmployeeComments());
            }
            if (updatedAssessment.getAdditionalComments() != null) {
                existingAssessment.setAdditionalComments(updatedAssessment.getAdditionalComments());
            }
            if (updatedAssessment.getCreatedBy() != null) {
                existingAssessment.setCreatedBy(updatedAssessment.getCreatedBy());
            }
            return selfAssessmentRepository.save(existingAssessment);
        } else {
            // Handle the case where the assessment with the given ID is not found
            // You can throw an exception or handle it based on your application's requirements
            // For now, returning null in case of not finding the assessment
            return null;
        }
    }


    public List<SelfAssessment> submit(List<SelfAssessment> updatedAssessments) {

        System.out.println("Received payload: " + updatedAssessments);
        List<SelfAssessment> updatedResults = new ArrayList<>();
        for (SelfAssessment updatedAssessment : updatedAssessments) {
            SelfAssessment savedAssessment = selfAssessmentRepository.save(updatedAssessment);
            updatedResults.add(savedAssessment);
//            Integer id = updatedAssessment.getQuestionId(); // Assuming questionId is used as the identifier
//            Optional<SelfAssessment> existingAssessmentOptional = selfAssessmentRepository.findById(id);
//
//            if (existingAssessmentOptional.isPresent()) {
//                SelfAssessment existingAssessment = existingAssessmentOptional.get();
//
//                // Update fields only if they are not null in the updatedAssessment
//                if (updatedAssessment.getQuestion() != null) {
//                    existingAssessment.setQuestion(updatedAssessment.getQuestion());
//                }
//                if (updatedAssessment.getStatus() != null) {
//                    existingAssessment.setStatus(updatedAssessment.getStatus());
//                }
//
//                if (updatedAssessment.getManagerComments() != null) {
//                    existingAssessment.setManagerComments(updatedAssessment.getManagerComments());
//                }
//                if (updatedAssessment.getManagerRating() != null) {
//                    existingAssessment.setManagerRating(updatedAssessment.getManagerRating());
//                }
//                if (updatedAssessment.getEmployeeRating() != null) {
//                    existingAssessment.setEmployeeRating(updatedAssessment.getEmployeeRating());
//                }
//                if (updatedAssessment.getEmployeeComments() != null) {
//                    existingAssessment.setEmployeeComments(updatedAssessment.getEmployeeComments());
//                }
//                if (updatedAssessment.getAdditionalComments() != null) {
//                    existingAssessment.setAdditionalComments(updatedAssessment.getAdditionalComments());
//                }
//                if (updatedAssessment.getCreatedBy() != null) {
//                    existingAssessment.setCreatedBy(updatedAssessment.getCreatedBy());
//                }
//                // Repeat for other fields
//
//                updatedResults.add(selfAssessmentRepository.save(existingAssessment));
//            }
            // If the existing assessment is not found, you may want to handle it based on your requirements
        }

        return updatedResults;
    }


    public AppraisalMaster getamStatus(Integer eid) {
         System.out.println(aprepo.findStatusrowById(Long.valueOf(eid)));
        return aprepo.findStatusrowById(Long.valueOf(eid));
    }

    public String getStatus(Integer eid) {
        return aprepo.findStatusById(Long.valueOf(eid));
    }


    public String changeStatus(Integer mid, String newStatus) {
        Optional<AppraisalMaster> optionalAppraisalMaster = aprepo.findById(Long.valueOf(mid));

        if (optionalAppraisalMaster.isPresent()) {
            AppraisalMaster appraisalMaster = optionalAppraisalMaster.get();
            System.out.println(appraisalMaster);
            String oldStatus = appraisalMaster.getStatus();
            appraisalMaster.setStatus(newStatus);
            System.out.println(appraisalMaster);
            aprepo.save(appraisalMaster);
            aprepo.flush();
            // No need to explicitly call save here, changes will be persisted
            return "Status changed from " + oldStatus + " to " + appraisalMaster.getStatus();
        } else {
            return "AppraisalMaster with ID " + mid + " not found";
        }
    }


    public Long getActiveAppraisalMasterId(Long employeeId) {
        Optional<AppraisalMaster> optionalAppraisalMaster = aprepo.findByEmployeeIdAndIsActive(employeeId, true);
        return optionalAppraisalMaster.map(AppraisalMaster::getId).orElse(null);
    }

    public List<SelfAssessment> getSelfAssessmentFormByMasterId(Integer masterId) {
        return selfAssessmentRepository.findByAppraisalMasterId(masterId);
    }


    public boolean deleteSelfAssessmentById(Integer id) {
        Optional<SelfAssessment> selfAssessmentOptional = selfAssessmentRepository.findById(id);

        if (selfAssessmentOptional.isPresent()) {
            selfAssessmentRepository.deleteById(id);
            return true; // Deletion successful
        } else {
            return false; // Question not found
        }
    }

    public void initializeAssessment(Long masterId) {

    }


//    public void initializeAssessment(Integer employeeId) {
//        Integer masterId = aprepo.findAppraisalIdByEmployeeID(employeeId);
//
//        if (masterId != null) {
//            // Assuming you have a method to retrieve a list of questions
//            List<SelfAssessment> questions = selfAssessmentRepository.findAll();
//
//            // If no questions are found, insert default questions
//            if (questions.isEmpty()) {
//                // Add your provided questions
//                SelfAssessment question1 = new SelfAssessment("How well did the employee contribute to project tasks and deadlines?");
//                SelfAssessment question2 = new SelfAssessment("Rate the employee's technical skills and expertise.");
//                SelfAssessment question3 = new SelfAssessment("In what ways did the employee collaborate with team members?");
//                SelfAssessment question4 = new SelfAssessment("Describe the employee's problem-solving abilities in challenging situations.");
//                SelfAssessment question5 = new SelfAssessment("Rate the overall communication and interpersonal skills of the employee.");
//
//                // Set the master ID for each default question
//                question1.setAppraisalMasterId(masterId);
//                question2.setAppraisalMasterId(masterId);
//                question3.setAppraisalMasterId(masterId);
//                question4.setAppraisalMasterId(masterId);
//                question5.setAppraisalMasterId(masterId);
//
//                // Set other properties for questions as needed
//
//                // Save the provided questions to the database
//                selfAssessmentRepository.save(question1);
//                selfAssessmentRepository.save(question2);
//                selfAssessmentRepository.save(question3);
//                selfAssessmentRepository.save(question4);
//                selfAssessmentRepository.save(question5);
//                // Save more questions as needed
//            }
//
//            for (SelfAssessment question : questions) {
//                // Set the master ID for each question
//                question.setAppraisalMasterId(masterId);
//                question.setStatus(null); // You might want to set the default status or leave it as null
//
//                // Set other properties for questions as needed
//
//                // Save the question to the database
//                selfAssessmentRepository.save(question);
//            }
//        } else {
//            // Handle the case where the master ID is not found based on your requirements
//            // You may throw an exception, log an error, or handle it as needed
//        }
//    }

    public List<SelfAssessment> submitWithDefaultQuestions(Long masterId) {
        List<SelfAssessment> defaultAssessments = getDefaultAssessments(masterId);
        List<SelfAssessment> updatedResults = new ArrayList<>();

        for (SelfAssessment defaultAssessment : defaultAssessments) {
            SelfAssessment savedAssessment = selfAssessmentRepository.save(defaultAssessment);
            updatedResults.add(savedAssessment);
        }

        return updatedResults;
    }

    private List<SelfAssessment> getDefaultAssessments(Long masterId) {
        List<SelfAssessment> defaultAssessments = new ArrayList<>();

        // Default weightage
        int weightage = 20;

        // Row 1
        defaultAssessments.add(new SelfAssessment(Math.toIntExact(masterId), "How well did the employee contribute to project tasks and deadlines?", "Pending", "", 0, "", 0, 0, "", weightage,null));

        // Row 2
        defaultAssessments.add(new SelfAssessment(Math.toIntExact(masterId), "Rate the employee's technical skills and expertise.", "Pending", "", 0, "", 0, 0, "", weightage,null));

        // Row 3
        defaultAssessments.add(new SelfAssessment(Math.toIntExact(masterId), "In what ways did the employee collaborate with team members?", "Pending", "", 0, "", 0, 0, "", weightage,null));

        // Row 4
        defaultAssessments.add(new SelfAssessment(Math.toIntExact(masterId), "Describe the employee's problem-solving abilities in challenging situations.", "Pending", "", 0, "", 0, 0, "", weightage,null));

        // Row 5
        defaultAssessments.add(new SelfAssessment(Math.toIntExact(masterId), "Rate the overall communication and interpersonal skills of the employee.", "Pending", "", 0, "", 0, 0, "", weightage,null));

        return defaultAssessments;
    }

//    public void uploadFile(Long selfAssessmentId, MultipartFile file) throws IOException {
//        // Validate selfAssessmentId and file
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty");
//        }
//
//        SelfAssessment selfAssessment = selfAssessmentRepository.findById(Math.toIntExact(selfAssessmentId))
//                .orElseThrow(() -> new IllegalArgumentException("SelfAssessment not found with id: " + selfAssessmentId));
//
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Path filePath = Paths.get("C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\attachments" + fileName);
//        Files.copy(file.getInputStream(), filePath);
//
//        selfAssessment.setFilePath(filePath.toString());
//        selfAssessmentRepository.save(selfAssessment);
//    }
public void uploadFile(Long selfAssessmentId, MultipartFile file) throws IOException {
    // Validate selfAssessmentId and file
    if (file.isEmpty()) {
        throw new IllegalArgumentException("File is empty");
    }

    SelfAssessment selfAssessment = selfAssessmentRepository.findById(Math.toIntExact(selfAssessmentId))
            .orElseThrow(() -> new IllegalArgumentException("SelfAssessment not found with id: " + selfAssessmentId));

    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
    String fileName = originalFileName;
    Path filePath = Paths.get("C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\attachments" + fileName);

    // Check if the file already exists, if so, append "(copy)" to the file name
    int count = 0;
    while (Files.exists(filePath)) {
        count++;
        String extension = "";
        int extensionIndex = originalFileName.lastIndexOf('.');
        if (extensionIndex != -1) {
            extension = originalFileName.substring(extensionIndex);
            fileName = originalFileName.substring(0, extensionIndex) + "(copy)" + count + extension;
        } else {
            fileName = originalFileName + "(copy)" + count;
        }
        filePath = Paths.get("C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\attachments\\" + fileName);
    }

    Files.copy(file.getInputStream(), filePath);

    selfAssessment.setFilePath(filePath.toString());
    selfAssessmentRepository.save(selfAssessment);
}

    public byte[] downloadFile(String filePath) throws IOException {
        // Validate file path
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path is null or empty");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (FileInputStream inputStream = new FileInputStream(file)) {
            return FileCopyUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            // Handle file reading exceptions
            throw new IOException("Error reading file: " + filePath, e);
        }
    }






}


package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.repository.SelfAssessmentRepository;

import com.sentrifugo.performanceManagement.vo.EmailController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Autowired
    private EmailController email;

    @Autowired
    private AppraisalEmpHistoryService appraisalEmpHistoryService;

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


    public List<SelfAssessment> submit(List<SelfAssessment> updatedAssessments) {

        System.out.println("Received payload: " + updatedAssessments);
        List<SelfAssessment> updatedResults = new ArrayList<>();
        for (SelfAssessment updatedAssessment : updatedAssessments) {
            SelfAssessment savedAssessment = selfAssessmentRepository.save(updatedAssessment);
            updatedResults.add(savedAssessment); }
        return updatedResults;
    }


    public AppraisalMaster getamStatus(Integer eid) {
        return aprepo.findStatusrowById(Long.valueOf(eid));
    }

    public String getStatus(Integer eid) {
        return aprepo.findStatusById(Long.valueOf(eid));
    }



    public Long getActiveAppraisalMasterId(Long employeeId) {
        try {
            Optional<AppraisalMaster> optionalAppraisalMaster = aprepo.findByEmployeeIdAndIsActive(employeeId, true);
            return optionalAppraisalMaster.map(AppraisalMaster::getId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public List<SelfAssessment> getSelfAssessmentFormByMasterId(Integer masterId) {
        return selfAssessmentRepository.findByAppraisalMasterId(masterId);
    }

    public Map<String, String> changeStatus(Integer mid, String newStatus) {
        Map<String, String> response = new HashMap<>();
        Optional<AppraisalMaster> optionalAppraisalMaster = aprepo.findById(Long.valueOf(mid));

        if (optionalAppraisalMaster.isPresent()) {
            AppraisalMaster appraisalMaster = optionalAppraisalMaster.get();
            String oldStatus = appraisalMaster.getStatus();
            appraisalMaster.setStatus(newStatus);
            aprepo.save(appraisalMaster);

            // Create a new AppraisalEmpHistory object for each employee updated status
            AppraisalEmpHistory appraisalEmpHistory = new AppraisalEmpHistory();
            appraisalEmpHistory.setAppraisalMasId(appraisalMaster.getId());
            appraisalEmpHistory.setEmpId(appraisalMaster.getEmployeeId());
            appraisalEmpHistory.setDate(new Date());
            appraisalEmpHistory.setStatus(newStatus);
            appraisalEmpHistory.setCreatedBy(appraisalMaster.getCreatedBy());
            appraisalEmpHistoryService.createAppraisalEmpHistory(appraisalEmpHistory);

            if(newStatus == "Employeesubmitted"){
                email.sendindivisualstatus(mid);
            }
            response.put("message", "success");
            response.put("result", "Status changed from " + oldStatus + " to " + appraisalMaster.getStatus());
        } else {
            response.put("message", "error");
            response.put("result", "AppraisalMaster with ID " + mid + " not found");
        }

        return response;
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

    //---------------------------------------------File Upload and Download Services---------------------------------------------------

    public  String uploadFile(MultipartFile file) throws IOException {
        // Validate file
        try{
            if (file.isEmpty()) {
                return "empty";
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = originalFileName.replaceAll(" ", "_");  // Replace spaces with underscores
            Path filePath = Paths.get("C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\attachments\\" + fileName);

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
            return fileName;}
        catch (Exception e){
            return "max__size";
        }
    }


    public FileSystemResource getFile(String fileName) {
        String filePath = "C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\attachments\\" + fileName;
        // Adjust the path based on your folder structureString absolutePath = "C:\\Users\\user\\Desktop\\performance-managament-system\\src\\main\\java\\com\\sentrifugo\\performanceManagement\\" + fileName;
        File file = new File(filePath);
        System.out.println(filePath);
        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            // Handle the case where the file is not found
            throw new RuntimeException("File not found: " + fileName);
        }
    }

    //------------------------------------------------Unused Services-----------------------------------------
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





}
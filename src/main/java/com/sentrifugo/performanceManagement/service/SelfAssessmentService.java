package com.sentrifugo.performanceManagement.service;
import jakarta.persistence.Column;
import org.springframework.beans.BeanUtils;
import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.SelfAssessmentRepository;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SelfAssessmentService {

    private final SelfAssessmentRepository selfAssessmentRepository;

    @Autowired
    public SelfAssessmentService(SelfAssessmentRepository selfAssessmentRepository) {
        this.selfAssessmentRepository = selfAssessmentRepository;
    }

    public List<SelfAssessment> getSelfAssessmentForm() {
        return selfAssessmentRepository.findAll();
    }

    public SelfAssessment getSelfAssessmentById(Integer id) {
        return selfAssessmentRepository.findById(id).orElse(null);
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
        List<SelfAssessment> updatedResults = new ArrayList<>();

        for (SelfAssessment updatedAssessment : updatedAssessments) {
            Integer id = updatedAssessment.getQuestionId(); // Assuming questionId is used as the identifier
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
                // Repeat for other fields

                updatedResults.add(selfAssessmentRepository.save(existingAssessment));
            }
            // If the existing assessment is not found, you may want to handle it based on your requirements
        }

        return updatedResults;
    }






}

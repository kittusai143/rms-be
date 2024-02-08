package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.SelfAssessment;
import com.sentrifugo.performanceManagement.repository.SelfAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String addQuestion(SelfAssessment newQuestion) {
        // Add any additional logic before saving to the database
        newQuestion.setStatus("not submitted");
        SelfAssessment new1=selfAssessmentRepository.save(newQuestion);
        System.out.println(new1);
        return "added qn"+newQuestion.getQuestion();
    }

    public List<SelfAssessment> submitSelfAssessmentForm(List<SelfAssessment> assessments) {
        // Add any additional logic before saving to the database
        System.out.print("Submitted all questions");
        return selfAssessmentRepository.saveAll(assessments);
    }


    public SelfAssessment updateSelfAssessment(Integer id, SelfAssessment updatedAssessment) {
        Optional<SelfAssessment> existingAssessmentOptional = selfAssessmentRepository.findById(id);

        if (existingAssessmentOptional.isPresent()) {
            SelfAssessment existingAssessment = existingAssessmentOptional.get();
            // Update the fields of the existing assessment with the values from the updated assessment
            existingAssessment.setStatus(updatedAssessment.getStatus());
            existingAssessment.setManagerComments(updatedAssessment.getManagerComments());
            existingAssessment.setManagerRating(updatedAssessment.getManagerRating());
            existingAssessment.setEmployeeComments(updatedAssessment.getEmployeeComments());
            existingAssessment.setEmployeeRating(updatedAssessment.getEmployeeRating());
            existingAssessment.setCreatedBy(updatedAssessment.getCreatedBy());
            existingAssessment.setAdditionalComments(updatedAssessment.getAdditionalComments());

            // Save the updated assessment back to the repository
            return selfAssessmentRepository.save(existingAssessment);
        } else {
            // Handle the case where the assessment with the given ID is not found
            // You can throw an exception or handle it based on your application's requirements
            // For now, returning null in case of not finding the assessment
            return null;
        }
    }

}

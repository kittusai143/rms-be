package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appraisal_master_ext")
public class SelfAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "appraisal_master_id")
    private Integer appraisalMasterId;

    @Column(name = "question")
    private String question;

    private String status;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "manager_rating")
    private Integer managerRating;

    @Column(name = "employee_comments")
    private String employeeComments;

    @Column(name = "employee_rating")
    private Integer employeeRating;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "additional_comments")
    private String additionalComments;

    @Column(name = "weightage")
    private Integer weightage;

//    @Value("${file.storage.path}")
    @Column(name = "file_path")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public SelfAssessment(String question) {
            this.question = question;
        }

    public SelfAssessment(Integer appraisalMasterId, String question, String status, String managerComments, Integer managerRating, String employeeComments, Integer employeeRating, Integer createdBy, String additionalComments, Integer weightage,String filePath) {
        this.appraisalMasterId = appraisalMasterId;
        this.question = question;
        this.status = status;
        this.managerComments = managerComments;
        this.managerRating = managerRating;
        this.employeeComments = employeeComments;
        this.employeeRating = employeeRating;
        this.createdBy = createdBy;
        this.additionalComments = additionalComments;
        this.weightage = weightage;
        this.filePath=filePath;
    }


    // Other methods and properties...
}


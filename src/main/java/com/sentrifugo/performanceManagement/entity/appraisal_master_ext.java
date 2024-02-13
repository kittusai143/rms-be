package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appraisal_master_ext")
public class appraisal_master_ext {

    private String question;
    @Id
    @Column(name="question_id")
    private Integer questionId;


    @Column(name = "appraisal_master_id")
    private Integer appraisalMasterId;

    private String status;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "manager_rating")
    private Integer managerRating;

    @Column(name = "employee_comments")
    private String employeeComments;

    @Column(name = "employee_rating")
    private Integer employeeRating;

    @Column(name ="additional_comments")
    private String additionalComments;



}
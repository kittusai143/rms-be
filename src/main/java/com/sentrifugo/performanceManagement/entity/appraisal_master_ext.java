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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "appraisal_master_id")
    private Integer appraisalMasterId;

    private String status;

    @Column(name = "parameter_response", columnDefinition = "nvarchar(max)")
    private String parameterResponse;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "manager_rating")
    private Integer managerRating;

    @Column(name = "employee_comments")
    private String employeeComments;

    @Column(name = "employee_rating")
    private Integer employeeRating;

    private Integer createdby;

    private Integer updatedby;


}
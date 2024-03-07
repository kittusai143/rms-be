package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@Table(name = "AppraisalEmpHistory", schema = "dbo")
public class AppraisalEmpHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appraisal_Hist_id")
    private Long id;

    @Column(name = "appraisalMasId")
    private Long appraisalMasId;

    @Column(name = "employee_id")
    private Long empId;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private String status;

    @Column(name = "createdBy")
    private Long createdBy;


}
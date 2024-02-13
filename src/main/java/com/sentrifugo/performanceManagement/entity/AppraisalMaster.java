package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appraisal_master", schema = "dbo")
public class AppraisalMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "appraisal_intitation_id")
    private Long appraisalIntitationId;

    @Column(name = "period")
    private Date period;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "isactive")
    private boolean isActive;

    @Column(name = "status")
    private String status;
}
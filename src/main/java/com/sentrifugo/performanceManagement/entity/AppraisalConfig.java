package com.sentrifugo.performanceManagement.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.springframework.web.bind.annotation.CrossOrigin;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@CrossOrigin
@Table(name = "appraisal_initiation", schema = "dbo")
public class AppraisalConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appraisal_config_id")
    private Long appraisalConfigId;

    @Column(name = "workflow_config_id")
    private Long workflowConfigId;
    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "appraisal_mode" , nullable = true)
    private String appraisalMode;

    @Column(name = "eligibility")
    private String eligibility;

    @Column(name = "status")
    private String status;

    @Column(name = "enable_to")
    private String enableTo;

    @Column(name = "from_year")
    private Date fromYear;

    @Column(name = "period")
    private Integer period;

    @Column(name = "manager_duedate")
    private Date managerDueDate;

    @Column(name = "employee_duedate")
    private Date employeeDueDate;

    @Column(name = "process_status")
    private String processStatus;

    @Column(name = "department")
    private String department;

    @Column(name = "to_year")
    private Date toYear;

    @Column(name = "parameter")
    private String parameter;

    @Column(name = "rating")
    private String rating;




}

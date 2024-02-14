package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema ="dbo",name="escalation_master")

public class EscalationMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name="appraisal_master_id")
    private Integer appraisalMasterId ;

    private String reason;

    private String   comments;

    @Column(name="initiated_by")
    private String  initiatedBy;

    @Column(name="created_by")
    private Integer  createdBy;

    @Column(name="updated_by")
    private Integer  updatedBy;

    private String   status;

    @Column(name="l2_manager_comments")
    private String  l2ManagerComments;
}

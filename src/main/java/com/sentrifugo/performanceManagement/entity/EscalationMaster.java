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

    private Integer appraisal_master_id ;

    private String reason;

    private String   comments;

    private String  initiated_by;

    private Integer  created_by;

    private Integer  updated_by;

    private String   status;

    private String  l2_manager_comments;
}

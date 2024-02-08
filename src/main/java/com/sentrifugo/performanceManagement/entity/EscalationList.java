package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "escalation_list")
public class EscalationList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="sil_id")
    private long id;

    @Column(name="employee_name")
    private String ename;


    @Column(name="designation")
    private String designation;


    @Column(name="department")
    private String department;


    @Column(name="escalated_by")
    private  String escalated_by;


    @Column(name="status")
    private String status;

}

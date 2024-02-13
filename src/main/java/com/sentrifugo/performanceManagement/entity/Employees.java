package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@CrossOrigin

@Table(name = "employee", schema = "dbo")
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reporting_manager")
    private Long reportingManager;

    @Column(name = "l2_manager")
    private Long l2Manager;

    @Column(name = "bussinessunit")
    private String businessUnit;

    @Column(name = "department")
    private String department;

    @Column(name = "isactive")
    private int isActive;

    @Column(name = "createdby")
    private Long createdBy;

    @Column(name = "updatedby")
    private Long updatedBy;

    @Column(name = "client")
    private String client;

    @Column(name = "project")
    private String project;
}


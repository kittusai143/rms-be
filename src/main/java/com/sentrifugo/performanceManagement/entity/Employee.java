package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee{
    @Id
    @Column(name="Id")
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "reporting_manager")
    private Integer reportingManager;

    @Column(name = "l2_manager")
    private Integer l2Manager;

    @Column(name = "businessunit")
    private String businessunit;

    @Column(name = "department")
    private String department;

    @Column(name = "isactive")
    private boolean isActive;

    @Column(name = "createdby")
    private Integer createdBy;

    @Column(name = "updatedby")
    private Integer updatedBy;

    @Column(name = "client")
    private String client;

    @Column(name = "project")
    private String project;

    @Column(name="date_of_joining")
    private Date dateOfJoining;

}

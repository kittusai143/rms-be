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

    @Column(name = "businessunit")
    private String businessunit;

    @Column(name = "client")
    private String client;

    @Column(name = "createdby")
    private Integer createdBy;

    @Column(name="date_of_joining")
    private Date dateOfJoining;

    @Column(name = "department")
    private String department;

    @Column(name = "isactive")
    private boolean isActive;

    @Column(name = "l2_manager")
    private Integer l2Manager;

    @Column(name = "project")
    private String project;

    @Column(name = "reporting_manager")
    private Integer reportingManager;

    @Column(name = "updatedby")
    private Integer updatedBy;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "skills")
    private String skills;

    @Column(name = "location")
    private String location;

}

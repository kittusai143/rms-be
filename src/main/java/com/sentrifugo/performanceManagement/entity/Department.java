package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
public class Department {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deptname")
    private String departmentName;

    @Column(name = "deptcode")
    private String departmentCode;

    @Column(name = "description")
    private String description;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "country")
    private Integer country;

    @Column(name = "state")
    private Integer state;

    @Column(name = "city")
    private Integer city;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "timezone")
    private Integer timezone;

    @Column(name = "depthead")
    private Integer departmentHead;

    @Column(name = "unitid")
    private Long unitId;

    @Column(name = "createdby")
    private Integer createdBy;

    @Column(name = "modifiedby")
    private Integer modifiedBy;

    @Column(name = "createddate")
    private Date createdDate;

    @Column(name = "modifieddate")
    private Date modifiedDate;

    @Column(name = "isactive")
    private boolean isActive;

}
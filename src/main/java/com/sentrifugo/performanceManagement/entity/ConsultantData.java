package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consultant_data")
public class ConsultantData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConsultantID")
    private Integer consultantID;

    @Column(name = "VendorID")
    private String vendorID;

    @Column(name = "ConsultantName")
    private String consultantName;

    @Column(name = "ConsultantContact")
    private String consultantContact;

    @Column(name = "ConsultantEmail")
    private String consultantEmail;

    @Column(name = "ConsultantExprience")
    private Float consultantExprience;

    @Column(name = "Role")
    private String role;

    @Column(name="vendorEmployeeType")
    private String vendorEmployeeType;

    @Column(name = "WorkLocation")
    private String workLocation;

    @Column(name = "status")
    private String status;
}



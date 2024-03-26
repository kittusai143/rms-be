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
@Table(name = "Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VendorId")
    private Long vendorId;

    @Column(name = "VendorName")
    private String vendorName;

    @Column(name = "DOJ")
    private Date doj;

    @Column(name = "VendorContact")
    private String vendorContact;

    @Column(name = "VendorEmail")
    private String vendorEmail;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "BillingStatus")
    private String billingStatus;

    @Column(name = "Department")
    private String department;

    @Column(name = "Designation")
    private String designation;

    @Column(name = "GroupName")
    private String groupName;

    @Column(name = "Line1Manager")
    private String line1Manager;

    @Column(name = "Name")
    private String name;

    @Column(name = "ProjectCode")
    private String projectCode;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "SilId")
    private String silId;

    @Column(name = "Team")
    private String team;

    @Column(name = "Vendor")
    private String vendor;

}

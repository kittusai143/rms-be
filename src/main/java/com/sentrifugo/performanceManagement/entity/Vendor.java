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
    @Column(name = "SilId")
    private String silId;

    @Column(name = "Name")
    private String name;

    @Column(name = "DOJ")
    private Date doj;

    @Column(name = "Designation")
    private String designation;

    @Column(name = "Department")
    private String department;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "ProjectCode")
    private String projectCode;

    @Column(name = "Team")
    private String team;

    @Column(name = "GroupName")
    private String groupName;

    @Column(name = "BillingStatus")
    private String billingStatus;

    @Column(name = "Line1Manager")
    private String line1Manager;

    @Column(name = "Vendor")
    private String vendor;

    @Column(name = "VendorContact")
    private String vendorContact;

    @Column(name = "VendorEmail")
    private String vendorEmail;

    @Column(name = "Comment")
    private String comment;

}

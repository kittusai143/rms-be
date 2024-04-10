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
@Table(name = "resource_allocation")
public class ResourceAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AllocationId")
    private Integer allocationId;

    @Column(name = "SilId")
    private String silId;

    @Column(name = "VendorID")
    private String vendorId;

    @Column(name = "ConsultantID")
    private String consultantId;

    @Column(name = "SowId")
    private String sowID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Role")
    private String role;

    @Column(name = "EmployeeType")
    private String employeeType;

    @Column(name = "DOJ")
    private Date doj;

    @Column(name = "Status")
    private boolean status;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "ProjectCode")
    private String projectCode;

    @Column(name = "BillingStartDate")
    private Date billingStartDate;

    @Column(name = "BillingEndDate")
    private Date billingEndDate;

    @Column(name = "Billability")
    private String billability;

    @Column(name = "Location")
    private String location;

    @Column(name = "ClientTimesheetAccess")
    private String clientTimesheetAccess;

    @Column(name = "PartnerEmailID")
    private String partnerEmailID;

    @Column(name = "ClientEmailID")
    private String clientEmailID;

    @Column(name = "Yubikey")
    private String yubikey;

    @Column(name = "YubikeySno")
    private String yubikeySno;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Skillset1")
    private String skillset1;

    @Column(name = "Skillset2")
    private String skillset2;

    @Column(name = "Training")
    private String training;

    @Column(name = "Certifications")
    private String certifications;

    @Column(name = "TechnologyDivision")
    private String technologydivision;

    @Column(name = "Awards")
    private String awards;

    @Column(name = "Audit")
    private String audit;

    @Column(name = "AllocationStatus")
    private String allocationStatus;

    @Column(name = "TechId")
    private Integer techMId;

    @Column(name = "YearsOfExp")
    private Integer yearsOfExp;
}

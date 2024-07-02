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
@Table(name = "vendor_data")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VendorId")
    private String vendorId;

    @Column(name = "VendorName")
    private String vendorName;

    @Column(name = "VendorOnBoardingDate")
    private Date vendorOnBoardingDate;

    @Column(name = "VendorContact")
    private String vendorContact;

    @Column(name = "VendorEmail")
    private String vendorEmail;

    @Column(name = "VendorType")
    private String vendorType;

    @Column(name="VendorStatus")
    private String vendorStatus;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "status")
    private String status;

}

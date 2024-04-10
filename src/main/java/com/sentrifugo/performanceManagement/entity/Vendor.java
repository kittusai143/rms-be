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
    @Column(name = "ID")
    private Long id;

    @Column(name = "VendorId")
    private String vendorId;

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

}

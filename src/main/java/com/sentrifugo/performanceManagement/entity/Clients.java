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
@Table(name = "client_data")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "ClientShortName")
    private String clientShortName;

    @Column(name = "ClientManager")
    private String clientManager;

    @Column(name = "ClientContactNumber")
    private String clientContactNumber;

    @Column(name = "ClientEmail")
    private String clientEmail;

    @Column(name = "ClientOnboardDate")
    private String clientOnboardDate;

    @Column(name = "status")
    private String status;

    @Column(name = "Role")
    private String role;

    @Column(name = "Comment")
    private String comment;

}

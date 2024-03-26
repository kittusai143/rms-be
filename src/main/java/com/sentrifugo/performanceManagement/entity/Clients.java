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
@Table(name = "Clients")
public class Clients {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "Partner")
    private String partner;

    @Column(name = "ClientManager")
    private String clientManager;

    @Column(name = "ClientContactNumber")
    private String clientContactNumber;

    @Column(name = "ClientEmail")
    private String clientEmail;

    @Column(name = "ClientOnboardDate")
    private Date clientOnboardDate;

    @Column(name = "ActiveProjects")
    private Integer activeProjects;

    @Column(name = "HeadCount")
    private Integer headCount;

}

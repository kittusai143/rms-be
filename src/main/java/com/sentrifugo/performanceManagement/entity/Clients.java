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
//    private Long id;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "Partner")
    private String partner;

    @Column(name = "ActiveProjects")
    private Integer activeProjects;

    @Column(name = "HeadCount")
    private Integer headCount;

    @Column(name = "ClientOnboardDate")
    private Date clientOnboardDate;

}

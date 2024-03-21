package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EmployeeReportingClients")
public class EmployeeReportingClients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "SilId")
    private String silId;

    @Column(name = "clientId")
    private Long clientId;

    @Column(name = "ProjectCode")
    private String projectCode;

}

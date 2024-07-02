package com.sentrifugo.performanceManagement.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sow_master")
public class SowMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name="SowId")
    private String sowId;

    @Column(name="SowStartDate")
    private LocalDate sowStartDate;

    @Column(name="SowEndDate")
    private LocalDate sowEndDate;

    @Column(name="ClientCode")
    private String clientCode;


}

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
@Table(name = "project_data")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ProjectCode")
    private String projectCode;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "ProjectObjective" )
    private String projectObjective;

    @Column(name = "ProjectStar")
    private Integer projectStar;

    @Column(name = "ProjectStartDate")
    private Date projectStartDate;

    @Column(name = "ProjectEndDate")
    private Date projectEndDate;

    @Column(name = "SOW")
    private boolean sow;

    @Column(name = "SOWStartDate")
    private Date sowStartDate;

    @Column(name = "SOWEndDate")
    private Date sowEndDate;

    @Column(name = "Duration")
    private Integer duration;

    @Column(name = "ProjectManager")
    private String projectManager;

    @Column(name = "DeliveryManager")
    private String deliveryManager;

    @Column(name = "TypeOfProject")
    private String typeOfProject;

    @Column(name = "SizeOfProjectHrs")
    private Integer sizeOfProjectHrs;

    @Column(name = "DomainId")
    private Integer domainId;

    @Column(name = "DomainName")
    private String domainName;
}
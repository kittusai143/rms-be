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
@Table(name = "project_data_")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "Sow_id")
    private String sowId;

    @Column(name = "projectStatus")
    private String projectStatus;

    @Column(name = "ProjectCode")
    private String projectCode;

    @Column(name = "DomainId")
    private Integer domainId;

    @Column(name = "Duration")
    private Integer duration;

    @Column(name = "DeliveryManager")
    private String deliveryManager;

    @Column(name = "DomainName")
    private String domainName;

    @Column(name = "ProjectEndDate")
    private Date projectEndDate;

    @Column(name = "ProjectManager")
    private String projectManager;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "ProjectObjective" )
    private String projectObjective;

    @Column(name = "ProjectStar")
    private Integer projectStar;

    @Column(name = "ProjectStartDate")
    private Date projectStartDate;

    @Column(name = "sizeOfProjectHrs")
    private Integer sizeOfProjectHrs;

    @Column(name = "sowstartdate")
    private Date sowStartDate;

    @Column(name = "sowenddate")
    private Date sowEndDate;

    @Column(name = "TypeOfProject")
    private String typeOfProject;

    @Column(name = "SOW")
    private boolean sow;

    @Column(name = "ClientName")
    private String clientName;

}
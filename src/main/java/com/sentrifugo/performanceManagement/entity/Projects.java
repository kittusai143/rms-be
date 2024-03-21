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
@Table(name = "Projects")
public class Projects {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
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

    @Column(name = "ClientManager")
    private String clientManager;

    @Column(name = "BillableHeadcount")
    private Integer billableHeadcount;

    @Column(name = "NonBillableHeadcount")
    private Integer nonBillableHeadcount;

    @Column(name = "TotalHeadcount")
    private Integer totalHeadcount;

    @Column(name = "TypeOfProject")
    private String typeOfProject;

    @Column(name = "SizeOfProjectHrs")
    private Integer sizeOfProjectHrs;

    @Column(name = "OffshoreHeadcount")
    private Integer offshoreHeadcount;

    @Column(name = "OnshoreHeadcount")
    private Integer onshoreHeadcount;

}
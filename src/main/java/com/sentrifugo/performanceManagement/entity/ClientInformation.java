package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ClientInformation")
public class ClientInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "Name")
    private String name;

    @Column(name = "EmailId")
    private String emailId;

    @Column(name = "Role")
    private String role;

    @Column(name = "ProjectCode")
    private String projectCode;

    @Column(name = "Status")
    private boolean status;

    @Column(name = "DSC")
    private String dsc;

    @Column(name = "PSC")
    private String psc;

    @Column(name = "MSC")
    private String msc;

    @Column(name = "POCfromSIL")
    private String pocFromSIL;

    @Column(name = "EscalationContactfromSIL")
    private String escalationContactFromSIL;

    @Column(name = "Comments")
    private String comments;

}

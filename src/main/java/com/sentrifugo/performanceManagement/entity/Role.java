package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin
@Table(name = "Role", schema = "dbo")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID")
    private  Integer roleID;

    @Column(name = "rolename")
    private String rolename;

    @Column(name = "roletype")
    private String roletype;

    @Column(name = "roleDescription")
    private String roleDescription;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "isactive")
    private boolean isActive;
}



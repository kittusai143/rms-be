package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private  Integer roleID;

        private String rolename;

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



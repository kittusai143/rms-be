package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Users", schema = "dbo")
public class Users {
    @Id
    @Column(name = "Id")
    private int Id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "empRole")
    private Integer empRole;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "userFullname")
    private String userFullName;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "isactive")
    private boolean isActive;

    @Column(name = "employeeId")
    private String employeeId;

    @Column(name = "password")
    private String password;

}

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
@Table(name="Users")
public class Users {
    @Id
    private int Id;
    private String email;
    private String name;
    private Integer empRole;
    private Date dob;
    private String userFullName;
    private String contactNumber;
    private boolean isActive;
    private String employeeId;

}

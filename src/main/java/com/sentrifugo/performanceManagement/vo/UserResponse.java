package com.sentrifugo.performanceManagement.vo;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String email;

    private String name;

    private Integer empRole;

    private Date dob;

    private String userFullName;

    private String contactNumber;

    private Boolean isActive;

    private String employeeId;


    private String password;

    private String roleName;

}

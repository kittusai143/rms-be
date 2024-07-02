package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndRoleDetailsDto {
    private Integer id;
    private Integer empRole;
    private String name;
    private String email;
    private String employeeId;
    private String empRoleName;
    private String empRoleType;


}

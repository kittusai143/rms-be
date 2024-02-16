package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDetails {
    private int id;
    private String name;
    private String email;
    private String l2Manager;
    private String client;
    private String project;
    private String reportingManager;
}
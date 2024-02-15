package com.sentrifugo.performanceManagement.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpDetails {
    private int id;
    private String name;
    private String email;
    private String l2Manager;
    private String reportingManager;
    private String project;
    private String client;
}

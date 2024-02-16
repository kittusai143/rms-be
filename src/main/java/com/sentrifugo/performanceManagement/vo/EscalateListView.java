package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor

public class EscalateListView {

    private String employeeId;

    private String employeeName;

    private String designation;

    private String department;

    private String escalationInitiatedBy;

    private String status;


}
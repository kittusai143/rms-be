package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.EmployeeReportingClients;
import com.sentrifugo.performanceManagement.service.EmployeeReportingClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("EmpRepClient")
public class EmployeeReportingClientsContorller {

    @Autowired
    private EmployeeReportingClientsService employeeReportingClientsService;
    @GetMapping("/getAll")
    public List<EmployeeReportingClients> getAllEmployeeReportingClients(){
        return employeeReportingClientsService.getAllEmployeeReportingClients();
    }

}

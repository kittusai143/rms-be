package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.EmployeeReportingClients;
import com.sentrifugo.performanceManagement.repository.EmployeeReportingClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeReportingClientsService {

    @Autowired
    private EmployeeReportingClientsRepository employeeReportingClientsRepository;

    public List<EmployeeReportingClients> getAllEmployeeReportingClients(){
        return employeeReportingClientsRepository.findAll();
    }

}

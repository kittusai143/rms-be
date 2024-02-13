package com.sentrifugo.performanceManagement.service;


import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.Employees;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public List<Employees> getEmployeesByBusinessUnitAndDepartment(String businessUnit, String department) {
        return employeeRepository.findByBusinessUnitAndDepartment(businessUnit, department);
    }

    public List<String> getDistinctDepartments() {
        return employeeRepository.findAll().stream()
                .map(Employees::getDepartment)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getDistinctBusinessUnits() {
        return employeeRepository.findAll().stream()
                .map(Employees::getBusinessUnit)
                .distinct()
                .collect(Collectors.toList());
    }
}


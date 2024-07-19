package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.repository.EmployeeTimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class EmployeeTimeSheetService {
    @Autowired
    private EmployeeTimeSheetRepository employeeTimeSheetRepository;
    public List<Map<String,Object>> getEmployeeData() {
        return employeeTimeSheetRepository.getEmployeeData();
    }

    public List<Map<String, Object>> getEmployee(String employeeName) {
        return employeeTimeSheetRepository.getEmployeeName(employeeName);
    }

    public List<Map<String, Object>> getEmployeeWeekData(String year, String month, String employeeName) {
        return employeeTimeSheetRepository.getEmployeeWeekData(year, month, employeeName);
    }

    public List<Map<String, Object>> getSumOfEmployee() {
        return employeeTimeSheetRepository.getSumOfEmployee();
    }
}


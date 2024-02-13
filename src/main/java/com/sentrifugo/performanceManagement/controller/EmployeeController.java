package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.Employees;
import com.sentrifugo.performanceManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    private AppraisalConfig appraisalConfig;
    @GetMapping("/all")
    public List<Employees> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/departments")
    public List<String> getDistinctDepartments() {
        return employeeService.getDistinctDepartments();
    }

    @GetMapping("/businessUnits")
    public List<String> getDistinctBusinessUnits() {
        return employeeService.getDistinctBusinessUnits();
    }

}
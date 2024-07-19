package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.service.EmployeeTimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("employeeTimesheet")
public class EmployeeTimeSheetController {
    @Autowired
    private EmployeeTimeSheetService employeeTimeSheetService;

    @GetMapping("/getEmployeeData")
    public List<Map<String,Object>> getEmployeeData() {
        return employeeTimeSheetService.getEmployeeData();
    }

    @GetMapping("/getEmployee/{employeeName}")
    public List<Map<String, Object>> getEmployee(@PathVariable String employeeName) {
        return employeeTimeSheetService.getEmployee(employeeName);
    }

    @GetMapping("getEmployeeWeekData/{year}/{month}/{employeeName}")
    public List<Map<String, Object>> getEmployeeWeekData(@PathVariable String year, @PathVariable String month, @PathVariable String employeeName) {
        return employeeTimeSheetService.getEmployeeWeekData(year, month, employeeName);
    }

    @GetMapping("getSumOfEmployee")
    public List<Map<String, Object>> getSumOfEmployee() {
        return employeeTimeSheetService.getSumOfEmployee();
    }
}

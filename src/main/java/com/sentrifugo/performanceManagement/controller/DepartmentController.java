package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Department;
import com.sentrifugo.performanceManagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("departments")
public class DepartmentController {

    @Autowired
    public DepartmentService departmentService;

    @GetMapping("/getAll")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartment();
    }
}

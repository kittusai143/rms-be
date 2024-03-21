package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Department;
import com.sentrifugo.performanceManagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentService {

    @Autowired
    public DepartmentRepository departmentRepository;

    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }
}

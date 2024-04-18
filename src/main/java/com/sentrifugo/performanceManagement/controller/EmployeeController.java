package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.repository.UsersRepository;
import com.sentrifugo.performanceManagement.service.EmployeeService;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private EmployeeService employeeService;

}
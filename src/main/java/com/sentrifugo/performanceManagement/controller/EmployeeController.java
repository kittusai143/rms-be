package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.repository.UsersRepository;
import com.sentrifugo.performanceManagement.service.EmployeeService;
import  com.sentrifugo.performanceManagement.vo.DistinctData;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.vo.ManagerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
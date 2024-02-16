package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.service.EmployeeService;
import  com.sentrifugo.performanceManagement.vo.DistinctData;
import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.vo.EmpDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("employee")
@CrossOrigin("*")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    EmployeeService employeeService;
    @GetMapping("/get")
    public List<EmpDetails> get(){
        return employeeService.getDetails();
    }
    @GetMapping("getByManager")
    public List<EmpDetails> getbymanager(@RequestParam(name="manager")int manager){
        return employeeService.getDetailsByManager(manager);
    }



    @PostMapping("add")
    public Employee add(@RequestBody Employee emp){
        System.out.println(emp);
        return employeeRepo.save(emp);
    }

    @PostMapping ("filter")
    public List<Employee> filter(
            @RequestBody DistinctData distinctData
    ){

        System.out.println("managers"+distinctData.getManagers());
        List<String> projects=distinctData.getProjects();
        List<String> clients=distinctData.getClients();
        List<String> managers=distinctData.getManagers();
        List<Integer> manager=employeeRepo.findIdsByManagerIn(managers);
        System.out.println(manager);
        List<Employee> users;

        if(projects.isEmpty()&&clients.isEmpty()&&managers.isEmpty()){
            //all the fields are empty
            return users= employeeRepo.findAll();
        }
        else if(projects.isEmpty()&&clients.isEmpty()){
            //projects and clients are empty
            return users=employeeRepo.findByReportingManagerIn(manager);
        }
        else if(clients.isEmpty()&&managers.isEmpty()){
            //clients and managers are empty
            return users=employeeRepo.findByProjectIn(projects);
        }
        else if(projects.isEmpty()&& managers.isEmpty()){
            //projects and managers are empty
            return users=employeeRepo.findByClientIn(clients);
        }
        else if(projects.isEmpty()){
            return users=employeeRepo.findByReportingManagerInAndClientIn(manager,clients);
        }
        else if(clients.isEmpty()){
            return users=employeeRepo.findByProjectInAndReportingManagerIn(projects,manager);
        }
        else if(managers.isEmpty()){
            return users=employeeRepo.findByProjectInAndClientIn(projects,clients);
        }
        else {
            return users=employeeRepo.findByProjectInAndReportingManagerInAndClientIn(projects,manager,clients);
        }
    }

    @Autowired
    private EmployeeService distinctDataService;

    @GetMapping("distinct")
    public ResponseEntity<DistinctData> getDistinctData() {
        DistinctData distinctData = distinctDataService.getDistinctData();
        return ResponseEntity.ok(distinctData);
    }


    @GetMapping("distinct/businessunits")
    public ResponseEntity<List<String>> getDistinctBusinessUnits() {
        List<String> businessUnits = employeeService.getDistinctBusinessUnit();
        return ResponseEntity.ok(businessUnits);
    }

    @GetMapping("distinct/departments")
    public ResponseEntity<List<String>> getDistinctDepartments() {
        List<String> departments = employeeService.getDistinctDepartment();
        return ResponseEntity.ok(departments);
    }
}

package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.service.EmployeeService;
import  com.sentrifugo.performanceManagement.vo.DistinctData;
import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
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
@RequestMapping("abhiram")
@CrossOrigin("*")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @GetMapping("get")
    public List<Employee> get(){
        return employeeRepo.findAll();
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
       List<String> projects=distinctData.getProjects();
       List<String> clients=distinctData.getClients();
       List<Integer> managers=distinctData.getManagers();
        List<Employee> users;

        if(projects.isEmpty()&&clients.isEmpty()&&managers.isEmpty()){
            //all the fields are empty
            return users= employeeRepo.findAll();
        }
        else if(projects.isEmpty()&&clients.isEmpty()){
            //projects and clients are empty
            return users=employeeRepo.findByReportingManagerIn(managers);
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
            return users=employeeRepo.findByReportingManagerInAndClientIn(managers,clients);
        }
        else if(clients.isEmpty()){
            return users=employeeRepo.findByProjectInAndReportingManagerIn(projects,managers);
        }
        else if(managers.isEmpty()){
            return users=employeeRepo.findByProjectInAndClientIn(projects,clients);
        }
        else {
            return users=employeeRepo.findByProjectInAndReportingManagerInAndClientIn(projects,managers,clients);
        }
    }



//    @PostMapping("/filter")
//    public Page<Employee> filter(@RequestBody DistinctData distinctData, @RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return employeeRepo.findByProjectInAndReportingManagerInAndClientIn(
//                distinctData.getProjects(), distinctData.getManagers(), distinctData.getClients());
//    }
    @GetMapping("page")
    public ResponseEntity<List<Employee>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        System.out.println(page+" "+size+" "+sortBy+" "+direction);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<Employee> userPage = employeeRepo.findAll(pageable);
        boolean isLastPage = !userPage.hasNext();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(userPage.getTotalPages()));
        headers.add("X-Is-Last-Page", String.valueOf(isLastPage));

        return ResponseEntity.ok()
                .headers(headers)
                .body(userPage.getContent());
//        return userPage.getContent();
    }
    @Autowired
    private EmployeeService distinctDataService;

    @GetMapping("distinct")
    public ResponseEntity<DistinctData> getDistinctData() {
        DistinctData distinctData = distinctDataService.getDistinctData();
        return ResponseEntity.ok(distinctData);
    }
}


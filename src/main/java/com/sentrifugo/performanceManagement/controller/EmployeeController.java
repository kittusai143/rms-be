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
        System.out.println(distinctData.getClients());
        System.out.println(distinctData.getProjects());
        System.out.println(distinctData.getManagers());
        return employeeRepo.findByProjectInAndReportingManagerInAndClientIn(distinctData.getProjects(),distinctData.getManagers(),distinctData.getClients());
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


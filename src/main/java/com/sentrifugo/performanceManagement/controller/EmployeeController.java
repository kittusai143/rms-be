package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.repository.UsersRepository;
import com.sentrifugo.performanceManagement.service.EmployeeService;
import  com.sentrifugo.performanceManagement.vo.DistinctData;
import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.vo.EmpDetails;
import com.sentrifugo.performanceManagement.vo.ManagerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("getByManager")
    public ResponseEntity<?> getByManager(@RequestBody ManagerFilter managerFilter) {
        try{
            List<Map<String,Object>> details;
            if (managerFilter.getClients().isEmpty() && managerFilter.getProjects().isEmpty()) {
                details = employeeService.getDetailsByManager(managerFilter.getManager());
                if (!details.isEmpty()) {
                    return ResponseEntity.ok(details);
                } else {
                    Map<String,String> map=new HashMap<>();
                    map.put("message","No employees are currently reporting to you");
                    List<Map<String,String>> list=new ArrayList<>();
                    list.add(map);
                    return ResponseEntity.ok(list);
                }
            } else if (managerFilter.getProjects().isEmpty()) {
                details = employeeService.getByClients(managerFilter.getManager(), managerFilter.getClients());
                if (!details.isEmpty()) {
                    return ResponseEntity.ok(details);
                } else {
                    Map<String,String> map=new HashMap<>();
                    map.put("message","No employees found for the specified clients");
                    List<Map<String,String>> list=new ArrayList<>();
                    list.add(map);
                    return ResponseEntity.ok(list);
                }
            } else if (managerFilter.getClients().isEmpty()) {
                details = employeeService.getByProjects(managerFilter.getManager(), managerFilter.getProjects());
                if (!details.isEmpty()) {
                    return ResponseEntity.ok(details);
                } else {
                    Map<String,String> map=new HashMap<>();
                    map.put("message","No employees found for the specified projects");
                    List<Map<String,String>> list=new ArrayList<>();
                    list.add(map);
                    return ResponseEntity.ok(list);
                }
            } else {
                details = employeeService.getByClientsAndProjects(managerFilter.getManager(), managerFilter.getClients(), managerFilter.getProjects());
                if (!details.isEmpty()) {
                    return ResponseEntity.ok(details);
                } else {
                    Map<String,String> map=new HashMap<>();
                    map.put("message","No employees found for the specified clients and projects");
                    List<Map<String,String>> list=new ArrayList<>();
                    list.add(map);
                    return ResponseEntity.ok(list);
                }
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }



    @PostMapping("filter")
    public ResponseEntity<?> filter(@RequestBody DistinctData distinctData) {
        try{
            List<Map<String,Object>> users;
            List<String> projects = distinctData.getProjects();
            List<String> clients = distinctData.getClients();
            List<String> managers = distinctData.getManagers();
            List<Integer> managerIds = employeeRepo.findIdsByManagerIn(managers);

            if (projects.isEmpty() && clients.isEmpty() && managers.isEmpty()) {
                users = employeeService.getDetails();
            } else if (projects.isEmpty() && clients.isEmpty()) {
                users = employeeService.findByManagerIn(managerIds);
            } else if (clients.isEmpty() && managers.isEmpty()) {
                users = employeeService.findByProjectIn(projects);
            } else if (projects.isEmpty() && managers.isEmpty()) {
                users = employeeService.findByClientIn(clients);
            } else if (projects.isEmpty()) {
                users = employeeService.findByManagerInAndClientIn(managerIds, clients);
            } else if (clients.isEmpty()) {
                users = employeeService.findByManagerInAndProjectIn(managerIds, projects);
            } else if (managers.isEmpty()) {
                users = employeeService.findByProjectInAndClientIn(projects, clients);
            } else {
                users = employeeService.findByManagerInAndProjectInAndClientIn(managerIds, projects, clients);
            }

            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                Map<String,String> map=new HashMap<>();
                List<Map<String,String>> list=new ArrayList<>();
                map.put("message","No employees found for the specified filters");
                list.add(map);
                return ResponseEntity.ok().body(list);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("distinct")
    public ResponseEntity<?> getDistinctData() {
        DistinctData distinctData = employeeService.getDistinctData();

        if (distinctData != null) {
            return ResponseEntity.ok(distinctData);
        } else {
            Map<String,String> map=new HashMap<>();
            map.put("message","No distinct data found");
            return ResponseEntity.ok().body(map);
        }
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

    @GetMapping("/getcounts")
    public Map<String, Integer> getAllCounts() {
        Map<String, Integer> countsMap = new HashMap<>();

        Integer empCount = employeeRepo.getTotalRecords();
        Integer deptCount = employeeRepo.getTotalDept();
        Integer reportingManagersCount = employeeRepo.getTotalReportingManagers();
        Integer l2ManagersCount = employeeRepo.getTotalL2Managers();

        // Add other count queries based on your requirements

        countsMap.put("employeeCount", empCount);
        countsMap.put("deptCount", deptCount);
        countsMap.put("l1ManagersCount", reportingManagersCount);
        countsMap.put("l2ManagersCount", l2ManagersCount);

        return countsMap;
    }
}
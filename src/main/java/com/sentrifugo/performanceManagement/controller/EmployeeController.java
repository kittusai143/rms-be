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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employee")
@CrossOrigin("*")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private EmployeeService employeeService;



    @GetMapping("/get")
    public ResponseEntity<?> get() {
        List<EmpDetails> details = employeeService.getDetails();

        if (!details.isEmpty()) {
            return ResponseEntity.ok(details); // 200 OK with data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee details found"); // 404 Not Found with error message
        }
    }


    @PostMapping("getByManager")
public ResponseEntity<?> getByManager(@RequestBody ManagerFilter managerFilter) {
    List<EmpDetails> details;
    if (managerFilter.getClients().isEmpty() && managerFilter.getProjects().isEmpty()) {
        details = employeeService.getDetailsByManager(managerFilter.getManager());
        if (!details.isEmpty()) {
            return ResponseEntity.ok(details);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found for the specified manager");
        }
    } else if (managerFilter.getProjects().isEmpty()) {
        details = employeeService.getByClients(managerFilter.getManager(), managerFilter.getClients());
        if (!details.isEmpty()) {
            return ResponseEntity.ok(details); // 200 OK with data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found for the specified manager and clients");
        }
    } else if (managerFilter.getClients().isEmpty()) {
        details = employeeService.getByProjects(managerFilter.getManager(), managerFilter.getProjects());
        if (!details.isEmpty()) {
            return ResponseEntity.ok(details); // 200 OK with data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found for the specified manager and projects");
        }
    } else {
        details = employeeService.getByClientsAndProjects(managerFilter.getManager(), managerFilter.getClients(), managerFilter.getProjects());
        if (!details.isEmpty()) {
            return ResponseEntity.ok(details); // 200 OK with data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found for the specified manager, clients, and projects");
        }
    }
}


    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody Employee emp) {

        Employee savedEmployee = employeeRepo.save(emp);

        if (savedEmployee != null) {
            return ResponseEntity.ok(savedEmployee); // 200 OK with saved employee data
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add employee"); // 500 Internal Server Error with error message
        }
    }


@PostMapping("filter")
public ResponseEntity<?> filter(@RequestBody DistinctData distinctData) {
    List<EmpDetails> users;
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
        return ResponseEntity.ok(users); // 200 OK with data
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found for the specified filters"); // 404 Not Found with error message
    }
}


    @GetMapping("page")
    public ResponseEntity<List<Employee>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {


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


@GetMapping("distinct")
public ResponseEntity<?> getDistinctData() {
    DistinctData distinctData = employeeService.getDistinctData();

    if (distinctData != null) {
        return ResponseEntity.ok(distinctData); // 200 OK with data
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No distinct data found"); // 404 Not Found with error message
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

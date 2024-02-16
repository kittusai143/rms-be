package com.sentrifugo.performanceManagement.service;



import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.repository.EmployeeRepository;
import com.sentrifugo.performanceManagement.vo.DistinctData;
import com.sentrifugo.performanceManagement.vo.EmpDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;
    @Autowired
    private UsersService usersService;

    public DistinctData getDistinctData() {
        DistinctData distinctData = new DistinctData();
        distinctData.setClients(employeeRepository.findDistinctByClient());
        distinctData.setProjects(employeeRepository.findDistinctByProject());
        distinctData.setManagers(employeeRepository.findDistinctByManager());
        return distinctData;
    }



    public List<EmpDetails> getDetails() {
        List<Object[]> results = employeeRepository.findEmpDetails();
        List<EmpDetails> details = new ArrayList<>();

        for (Object[] result : results) {
            EmpDetails empDetails = new EmpDetails();
            empDetails.setId((Integer) result[0]);
            empDetails.setName((String) result[1]);
            empDetails.setEmail((String) result[2]);
            empDetails.setClient((String) result[3]);
            empDetails.setProject((String) result[4]);
            empDetails.setReportingManager((String) result[5]);
            empDetails.setL2Manager((String) result[6]);

            details.add(empDetails);
        }

        return details;
    }

    public List<EmpDetails> getDetailsByManager(int manager) {
        List<Object[]> results = employeeRepository.findEmpDetailsByManager(manager);
        List<EmpDetails> details = new ArrayList<>();

        for (Object[] result : results) {
            EmpDetails empDetails = new EmpDetails();
            empDetails.setName((String) result[1]);
            empDetails.setEmail((String) result[2]);
            empDetails.setClient((String) result[3]);
            empDetails.setProject((String) result[4]);
            empDetails.setReportingManager((String) result[5]);
            empDetails.setL2Manager((String) result[6]);
            details.add(empDetails);
        }

        return details;
    }

    public List<Employee> getEmployeesByBusinessUnitAndDepartmentAndRoleId(
            String businessunit, String department, Integer roleId) {
        List<Users> users = usersService.getUsersByRoleId(roleId);
        List<Integer> userIds = users.stream().map(Users::getId).collect(Collectors.toList());
        return employeeRepository.findByUserIdInAndBusinessunitAndDepartment(userIds, businessunit, department);
    }


    public List<String> getDistinctBusinessUnit() {
        return employeeRepository.findDistinctBusinessunit();
    }

    public List<String> getDistinctDepartment() {
        return employeeRepository.findDistinctDepartment();
    }
}


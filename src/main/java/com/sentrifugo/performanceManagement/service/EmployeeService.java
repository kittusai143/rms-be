package com.sentrifugo.performanceManagement.service;



import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.vo.DistinctData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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


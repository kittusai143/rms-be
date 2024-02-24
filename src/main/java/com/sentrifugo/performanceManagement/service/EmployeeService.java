package com.sentrifugo.performanceManagement.service;



import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.EmployeeRepo;
import com.sentrifugo.performanceManagement.vo.DistinctData;
import com.sentrifugo.performanceManagement.vo.EmpDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


    public List<Map<String,Object>> getDetails() {
        List<Map<String,Object>> details = employeeRepository.findEmpDetails();
        return details;
    }
    public List<Map<String,Object>> findByProjectIn(List<String> projects) {
        List<Map<String,Object>> details = employeeRepository.findByProjectIn(projects);
        return details;
    }
    public List<Map<String,Object>> findByClientIn(List<String> clients) {
        List<Map<String,Object>> details = employeeRepository.findByClientIn(clients);
        return details;
    }
    public List<Map<String,Object>> findByManagerIn(List<Integer> managers) {
        List<Map<String,Object>> details = employeeRepository.findByReportingManagerIn(managers);
        return details;
    }
    public List<Map<String,Object>> findByManagerInAndClientIn(List<Integer> managers,List<String >clients) {
        List<Map<String,Object>> details = employeeRepository.findByReportingManagerInAndClientIn(managers,clients);
        return details;
    }
    public List<Map<String,Object>> findByProjectInAndClientIn(List<String> projects,List<String >clients) {
        List<Map<String,Object>> details = employeeRepository.findByProjectInAndClientIn(projects,clients);
        return details;
    }
    public List<Map<String,Object>> findByManagerInAndProjectIn(List<Integer> managers,List<String >projects) {
        List<Map<String,Object>>  details = employeeRepository.findByProjectInAndReportingManagerIn(projects,managers);
        return details;
    }
    public List<Map<String,Object>> findByManagerInAndProjectInAndClientIn(List<Integer> managers,List<String >projects,List<String> clients) {
        List<Map<String,Object>> details = employeeRepository.findByProjectInAndReportingManagerInAndClientIn(projects,managers,clients);
        return details;
    }

    //METHODS FOR TEAM OF A PARTICULAR MANAGER
    public List<Map<String,Object>> getDetailsByManager(int manager) {
        List<Map<String,Object>> details = employeeRepository.findEmpDetailsByManager(manager);
        return details;
    }

    public List<Map<String,Object>> getByProjects(int manager,List<String> projects){
        List<Map<String,Object>> details=employeeRepository.filterByProject(manager,projects);
        return details;
    }
    public List<Map<String ,Object>> getByClients(int manager,List<String> clients){
        List<Map<String,Object>> details=employeeRepository.filterByClient(manager,clients);
        return details;
    }
    public List<Map<String,Object>> getByClientsAndProjects(int manager,List<String> clients,List<String> projects){
        List<Map<String ,Object>> details=employeeRepository.filterByClientAndProject(manager,projects,clients);
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


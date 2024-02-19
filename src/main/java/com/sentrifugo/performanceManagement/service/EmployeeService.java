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
    public List<EmpDetails> findByProjectIn(List<String> projects) {
        List<Object[]> results = employeeRepository.findByProjectIn(projects);
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
    public List<EmpDetails> findByClientIn(List<String> clients) {
        List<Object[]> results = employeeRepository.findByClientIn(clients);
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
    public List<EmpDetails> findByManagerIn(List<Integer> managers) {
        List<Object[]> results = employeeRepository.findByReportingManagerIn(managers);
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
    public List<EmpDetails> findByManagerInAndClientIn(List<Integer> managers,List<String >clients) {
        List<Object[]> results = employeeRepository.findByReportingManagerInAndClientIn(managers,clients);
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
    public List<EmpDetails> findByProjectInAndClientIn(List<String> projects,List<String >clients) {
        List<Object[]> results = employeeRepository.findByProjectInAndClientIn(projects,clients);
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
    public List<EmpDetails> findByManagerInAndProjectIn(List<Integer> managers,List<String >projects) {
        List<Object[]> results = employeeRepository.findByProjectInAndReportingManagerIn(projects,managers);
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
    public List<EmpDetails> findByManagerInAndProjectInAndClientIn(List<Integer> managers,List<String >projects,List<String> clients) {
        List<Object[]> results = employeeRepository.findByProjectInAndReportingManagerInAndClientIn(projects,managers,clients);
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





    //METHODS FOR TEAM OF A PARTICULAR MANAGER
    public List<EmpDetails> getDetailsByManager(int manager) {
        List<Object[]> results = employeeRepository.findEmpDetailsByManager(manager);
        List<EmpDetails> details = new ArrayList<>();

        for (Object[] result : results) {
            EmpDetails empDetails = new EmpDetails();
            System.out.println("Id"+result[0]);
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

    public List<EmpDetails> getByProjects(int manager,List<String> projects){
        List<Object[]> results=employeeRepository.filterByProject(manager,projects);
        List<EmpDetails> details=new ArrayList<>();
        for(Object[] result:results){
            EmpDetails empDetails=new EmpDetails();
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
    public List<EmpDetails> getByClients(int manager,List<String> clients){
        List<Object[]> results=employeeRepository.filterByClient(manager,clients);
        List<EmpDetails> details=new ArrayList<>();
        for(Object[] result:results){
            EmpDetails empDetails=new EmpDetails();
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
    public List<EmpDetails> getByClientsAndProjects(int manager,List<String> clients,List<String> projects){
        List<Object[]> results=employeeRepository.filterByClientAndProject(manager,projects,clients);
        List<EmpDetails> details=new ArrayList<>();
        for(Object[] result:results){
            EmpDetails empDetails=new EmpDetails();
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


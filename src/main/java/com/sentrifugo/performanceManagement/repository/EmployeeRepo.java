package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    List<Employee> findByProject(String project);


    List<Employee> findByProjectAndClient(String project, String client);

//    List<Employee> findByProjectAndManager(String project, String manager);

//    List<Employee> findByProjectAndManagerAndClient(String project, String manager, String client);

//    List<Employee> findByManagerAndClient(String manager, String client);

//    List<Employee> findByManager(String manager);
//    List<Employee> findByReportingManager(String manager);

//    @Query("SELECT DISTINCT e.manager FROM Employee e")
//    List<String> findDistinctByManager();

    @Query("SELECT DISTINCT e.client FROM Employee e")
    List<String> findDistinctByClient();

    @Query("SELECT DISTINCT e.project FROM Employee e")
    List<String> findDistinctByProject();

    @Query("SELECT DISTINCT e.reportingManager FROM Employee e")
    List<Integer> findDistinctByManager();



    List<Employee> findByProjectInAndReportingManagerInAndClientIn(List<String> projects, List<Integer> managers, List<String> clients);


}

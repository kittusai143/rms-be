package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    List<Employee> findByProject(String project);


    List<Employee> findByProjectAndClient(String project, String client);

    @Query("SELECT DISTINCT e.client FROM Employee e")
    List<String> findDistinctByClient();

    @Query("SELECT DISTINCT e.project FROM Employee e")
    List<String> findDistinctByProject();

    @Query("SELECT DISTINCT e.reportingManager FROM Employee e")
    List<Integer> findDistinctByManager();


    List<Employee> findByClientIn(List<String> clients);
    List<Employee> findByProjectIn(List<String> clients);
    List<Employee> findByReportingManagerIn(List<Integer> managers);
    List<Employee> findByReportingManagerInAndClientIn( List<Integer> managers, List<String> clients);
    List<Employee>findByProjectInAndClientIn(List<String> projects,List<String> clients);
    List<Employee> findByProjectInAndReportingManagerIn(List<String> projects, List<Integer> managers);
    List<Employee> findByProjectInAndReportingManagerInAndClientIn(List<String> projects, List<Integer> managers, List<String> clients);
    @Query("SELECT DISTINCT e.businessunit FROM Employee e")
    List<String> findDistinctBusinessunit();
    @Query("SELECT DISTINCT e.department FROM Employee e")
    List<String> findDistinctDepartment();
    @Query("SELECT e FROM Employee e WHERE e.user_id IN :userIds AND e.businessunit = :businessunit AND e.department = :department")
    List<Employee> findByUserIdInAndBusinessunitAndDepartment(List<Integer> userIds, String businessunit, String department);


}

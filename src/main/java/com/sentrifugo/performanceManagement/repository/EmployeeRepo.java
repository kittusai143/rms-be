
package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.vo.EmpDetails;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
//FIELDS IN EMPLOYEE
//id
//user_id
//reportingManager
//l2Manager
//businessunit,department,isActive,createdBy,updatedBy,client,project



//FIELDS IN USERS
//private Integer id;
//private String email;
//private String name;
//private Integer empRole;
//private Date dob;
//@Column(name = "userfullname")
//private String userFullName;
//private String contactNumber;
//private boolean isActive;
//private String employeeId;
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    List<Employee> findByProject(String project);


    //@Query("SELECT new com.sentrifugo.performanceManagement.vo.EmpDetails(" +
//        "u.id, u.name, u.email, e.client, e.project) " +
//        "FROM Users u " +
//        "JOIN Employee e ON u.id = e.id")
//List<EmpDetails> findEmpDetails();
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,Users.name as L2_Manager from(select E.*,Users.name as ReportingManager  from (select Users.id,Users.name,employee.reporting_manager,employee.l2_manager,Users.email,employee.client,employee.project   from Users join employee on Users.id=employee.Id where employee.reporting_manager=:manager ) as E join Users on Users.id=E.reporting_manager)as T join Users on T.l2_manager=Users.Id ",nativeQuery = true)
    List<Object[]> findEmpDetailsByManager(int manager);

    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,Users.name as L2_Manager from(select E.*,Users.name as ReportingManager  from (select Users.id,Users.name,employee.reporting_manager,employee.l2_manager,Users.email,employee.client,employee.project   from Users join employee on Users.id=employee.Id ) as E join Users on Users.id=E.reporting_manager)as T join Users on T.l2_manager=Users.Id",nativeQuery = true)
    List<Object[]> findEmpDetails();


    List<Employee> findByProjectAndClient(String project, String client);

    @Query("SELECT DISTINCT e.client FROM Employee e")
    List<String> findDistinctByClient();

    @Query("SELECT DISTINCT e.project FROM Employee e")
    List<String> findDistinctByProject();

    @Query("select distinct u.name from Employee e join Users u on e.reportingManager=u.Id")
    List<String> findDistinctByManager();

    @Query("SELECT e.id FROM Users e  WHERE e.name IN :managerNames")
    List<Integer> findIdsByManagerIn(@Param("managerNames") List<String> managerNames);

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
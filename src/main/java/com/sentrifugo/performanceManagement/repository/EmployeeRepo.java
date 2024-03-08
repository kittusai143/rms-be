
package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.vo.EmpDetails;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Date;
import java.util.List;
import java.util.Map;
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

    @Query("SELECT COUNT(emp.id) FROM Employee emp")
    Integer getTotalRecords();
    @Query("SELECT COUNT(DISTINCT e.department) FROM Employee e")
    Integer getTotalDept();

    @Query("SELECT COUNT(DISTINCT e.reportingManager) FROM Employee e")
    Integer getTotalReportingManagers();

    @Query("SELECT COUNT(DISTINCT e.l2Manager) FROM Employee e")
    Integer getTotalL2Managers();
    List<Employee> findByProject(String project);

    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findEmpDetails();

    @Query("SELECT DISTINCT e.client FROM Employee e")
    List<String> findDistinctByClient();

    @Query("SELECT DISTINCT e.project FROM Employee e")
    List<String> findDistinctByProject();
    @Query (value= "SELECT Id from Employee where user_id=:id")
    Integer getEmpId(Integer id );

    @Query("select distinct u.name from Employee e join Users u on e.reportingManager=u.Id")
    List<String> findDistinctByManager();

    @Query("SELECT e.id FROM Users e  WHERE e.name IN :managerNames")
    List<Integer> findIdsByManagerIn(@Param("managerNames") List<String> managerNames);
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id and employee.client in :clients ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findByClientIn(List<String> clients);

    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id and employee.project in :projects ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findByProjectIn(List<String> projects);

    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id and employee.reporting_manager in :managers ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findByReportingManagerIn(List<Integer> managers);
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id and employee.reporting_manager in :managers and client in :clients ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findByReportingManagerInAndClientIn( List<Integer> managers, List<String> clients);
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id  and employee.client in :clients and employee.project in :projects ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>>findByProjectInAndClientIn(List<String> projects,List<String> clients);
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id and employee.reporting_manager in :managers  and employee.project in :projects ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findByProjectInAndReportingManagerIn(List<String> projects, List<Integer> managers);
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id and employee.reporting_manager in :managers and employee.client in :clients and employee.project in :projects ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id",nativeQuery = true)
    List<Map<String,Object>> findByProjectInAndReportingManagerInAndClientIn(List<String> projects, List<Integer> managers, List<String> clients);


    //METHODS FOR THE TEAM OF A PARTICULAR MANAGER
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id where employee.reporting_manager=:manager ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id ",nativeQuery = true)
    List<Map<String,Object>> findEmpDetailsByManager(int manager);

    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id where employee.reporting_manager=:manager and employee.client in :clients and employee.project in :projects ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id ",nativeQuery = true)
    List<Map<String,Object>> filterByClientAndProject(int manager,List<String> projects,List<String >clients);

    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id where employee.reporting_manager=:manager and  employee.project in :projects ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id ",nativeQuery = true)
    List<Map<String,Object>> filterByProject(int manager,List<String> projects);
    @Query(value="select T.id,T.name,T.email,T.client,T.project,T.ReportingManager,users.name as L2_Manager from(select E.*,users.name as ReportingManager  from (select users.id,users.name,employee.reporting_manager,employee.l2_manager,users.email,employee.client,employee.project   from users join employee on users.id=employee.user_id where employee.reporting_manager=:manager and  employee.client in :clients ) as E join users on users.id=E.reporting_manager)as T join users on T.l2_manager=users.Id ",nativeQuery = true)
    List<Map<String,Object>> filterByClient(int manager,List<String> clients);

    @Query("SELECT DISTINCT e.department FROM Employee e")
    List<String> findDistinctByDepartment();

    @Query("SELECT DISTINCT e.businessunit FROM Employee e")
    List<String> findDistinctByDesignation();

    @Query("SELECT DISTINCT e.businessunit FROM Employee e")
    List<String> findDistinctBusinessunit();
    @Query("SELECT DISTINCT e.department FROM Employee e")
    List<String> findDistinctDepartment();
    @Query("SELECT e FROM Employee e WHERE e.user_id IN :userIds AND e.businessunit = :businessunit AND e.department IN :departments AND e.dateOfJoining >= :dateOfJoining")
    List<Employee> findByUserIdInAndBusinessunitAndDepartmentInAndDateOfJoining(List<Integer> userIds, String businessunit, List<String> departments, Date dateOfJoining );

}
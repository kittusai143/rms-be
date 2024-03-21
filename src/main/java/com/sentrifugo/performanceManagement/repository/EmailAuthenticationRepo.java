package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import com.sentrifugo.performanceManagement.vo.UserDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface EmailAuthenticationRepo extends JpaRepository<Users,Integer> {
    @Query(value="SELECT u.id, e.id AS EmpId, u.empRole, r.rolename AS empRoleName, r.roletype AS empRoleType, u.name, u.email, u.employeeId " +
            "FROM users u " +
            "JOIN role r ON u.empRole = r.roleID " +  // Added space here
            "JOIN employee e ON u.id = e.user_id " +   // Added space here
            "WHERE u.email = :email", nativeQuery = true)
    Map<String,Object> findDetailsBYEmail(String email);

    @Query(value="SELECT u.id, u.name,u.employeeId, e.businessunit, e.department " +
            "FROM users u " +
            "JOIN employee e ON u.id = e.user_id " +
            "WHERE u.id = :id", nativeQuery = true)
    Map<String,Object> findDetailsById(@Param("id") Integer id);


    @Query(value = "SELECT reporting_users.name " +
            "FROM users u " +
            "JOIN employee e ON u.id = e.user_id " +
            "JOIN users AS reporting_users ON e.reporting_manager= reporting_users.id " +
            "WHERE u.Id = :Id", nativeQuery = true)
    List<String> getReportingManagerNames(Integer Id);




    @Query(value = "SELECT l2manager_users.name " +
            "FROM Users u " +
            "JOIN employee e ON u.id = e.user_id " +
            "JOIN Users AS l2manager_users ON e.l2_manager= l2manager_users.id " +
            "WHERE u.Id = :Id", nativeQuery = true)
    List<String> getL2ManagerNames(Integer Id);


}

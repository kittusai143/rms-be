package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import com.sentrifugo.performanceManagement.vo.UserDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailAuthenticationRepo extends JpaRepository<Users,Integer> {
    @Query("SELECT  u.id, u.empRole, r.rolename AS empRoleName, r.roletype AS empRoleType, u.name, u.email,  u.employeeId " +
            "FROM Users u " +
            "JOIN Role r ON u.empRole = r.roleID " +
            "WHERE u.email = :email")
    List<String> findDetailsBYEmail(String email);

    @Query("SELECT u.id, u.name,u.employeeId, e.bussinessunit, e.department " +
            "FROM Users u " +
            "JOIN employee e ON u.id = e.user_id " +
            "WHERE u.id = :id")
    List<String> findDetailsById(@Param("id") Integer id);


    @Query(value = "SELECT reporting_users.name " +
            "FROM Users u " +
            "JOIN employee e ON u.id = e.user_id " +
            "JOIN Users AS reporting_users ON e.reporting_manager= reporting_users.id " +
            "WHERE u.Id = :Id", nativeQuery = true)
    List<String> getReportingManagerNames(Integer Id);




    @Query(value = "SELECT l2manager_users.name " +
            "FROM Users u " +
            "JOIN employee e ON u.id = e.user_id " +
            "JOIN Users AS l2manager_users ON e.l2_manager= l2manager_users.id " +
            "WHERE u.Id = :Id", nativeQuery = true)
    List<String> getL2ManagerNames(Integer Id);


}

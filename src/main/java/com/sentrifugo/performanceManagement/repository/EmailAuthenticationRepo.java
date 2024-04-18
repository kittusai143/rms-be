package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface EmailAuthenticationRepo extends JpaRepository<Users,Integer> {
    @Query(value="SELECT u.id, u.empRole, r.rolename AS empRoleName, r.roletype AS empRoleType, u.name, u.email, u.employeeId " +
            "FROM users u " +
            "JOIN role r ON u.empRole = r.roleID " +  // Added space here
            "WHERE u.email = :email", nativeQuery = true)
    Map<String,Object> findDetailsBYEmail(String email);

    @Query(value="SELECT u.*" +
            "FROM users u " +
            "WHERE u.id = :id", nativeQuery = true)
    Map<String,Object> findDetailsById(@Param("id") Integer id);


}

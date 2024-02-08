package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailAuthenticationRepo extends JpaRepository<Users,Integer> {
    @Query("SELECT u.id, u.empRole, r.rolename AS empRoleName, r.roletype AS empRoleType, u.name, u.email, u.employeeId " +
            "FROM Users u " +
            "JOIN Role r ON u.empRole = r.roleID " +
            "WHERE u.email = :email")
    List<UserAndRoleDetailsDto> findDetailsBYEmail(String email);
}

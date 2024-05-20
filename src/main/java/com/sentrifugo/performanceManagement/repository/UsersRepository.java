package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByEmpRole(Integer empRole);


    @Query("select name from Users where email=:email")
    String findByEmailJWT(String email);

   Users findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.employeeId = :employeeId")
    Users findByEmployeeId(String employeeId);


@Query("SELECT u.userFullName AS fullName, u.email AS email FROM Users u WHERE MONTH(u.dob) = MONTH(:date) AND DAY(u.dob) = DAY(:date) AND u.isActive = true")
List<Map<String, Object>> findActiveUsersByBirthday(@Param("date") Date date);

    @Query("SELECT u.userFullName AS fullname, u.email AS email, YEAR(:date) - YEAR(e.dateOfJoining) AS anniversaryYear " +
            "FROM Employee e " +
            "JOIN Users u ON e.user_id = u.Id " +
            "WHERE MONTH(e.dateOfJoining) = MONTH(:date) AND DAY(e.dateOfJoining) = DAY(:date)")
    List<Map<String, Object>> findEmployeesWithAnniversaryByDate(@Param("date") Date date);
}
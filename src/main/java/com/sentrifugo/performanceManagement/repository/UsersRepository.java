package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByEmpRole(Integer empRole);



    @Query(value = "SELECT T.id, T.name, A.status " +
            "FROM ( " +
            "    SELECT E.* " +
            "    FROM ( " +
            "        SELECT users.id, users.name " +
            "        FROM users " +
            "        JOIN employee ON users.id = employee.Id " +
            "        WHERE employee.reporting_manager = :managerId " +
            "    ) AS E " +
            ") AS T " +
            "LEFT JOIN appraisal_master AS A ON T.id = A.employee_id", nativeQuery = true)
    List<Map<String,Object>> findDetailsWithStatusByManagerId(Integer managerId);

    @Query(value="select Id,name from users where empRole=4",nativeQuery = true)
    List<Map<String,Object>> findManagers();


    @Query(value = "SELECT users.name,appraisal_master.status " +
            "FROM users " + "JOIN  appraisal_master ON users.Id = appraisal_master.employee_id " +
            "WHERE empRole = 1;",nativeQuery = true)
    List<Map<String,Object>> find();


    @Query("select name from Users where email=:email")
    String findByEmailJWT(String email);

    @Query(value="SELECT users.email,users.name " +
            "FROM users " +
            "JOIN appraisal_master ON users.Id = appraisal_master.employee_id " +
            "WHERE appraisal_master.status = 'Initialized' ", nativeQuery=true)
    List<Map<String,String>> findbystatus();
    @Query(value = "SELECT u.name as employeename, rm.name as managername, u.email AS employee_email, rm.email AS reporting_manager_email, am.status " +
            "FROM appraisal_master am " +
            "JOIN employee e ON am.employee_id = e.Id " +
            "JOIN users u ON e.user_id = u.Id " +
            "JOIN users rm ON e.reporting_manager = rm.Id " +
            "WHERE am.Id =:id", nativeQuery = true)
    Map<String,String> findbymanageremployeestatus(Integer id);

   Users findByEmail(String email);
    @Query(value="SELECT T.id, T.name, A.status FROM ( " +
            "                   SELECT users.id, users.name " +
            "                   FROM users " +
            "                    JOIN employee ON users.id = employee.Id " +
            "            ) AS T " +
            "            LEFT JOIN appraisal_master AS A ON T.id = A.employee_id",nativeQuery = true)
    List<Map<String,String>> findDetailsWithStatus();


}
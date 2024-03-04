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
            "        SELECT Users.id, Users.name " +
            "        FROM Users " +
            "        JOIN employee ON Users.id = employee.Id " +
            "        WHERE employee.reporting_manager = :managerId " +
            "    ) AS E " +
            ") AS T " +
            "LEFT JOIN appraisal_master AS A ON T.id = A.employee_id", nativeQuery = true)
    List<Map<String,Object>> findDetailsWithStatusByManagerId(Integer managerId);



//    @Query(value="SELECT T.id, T.name, A.status FROM ( " +
//            "                   SELECT Users.id, Users.name " +
//            "                   FROM Users " +
//            "                    JOIN employee ON Users.id = employee.Id " +
//            "            ) AS T " +
//            "            LEFT JOIN appraisal_master AS A ON T.id = A.employee_id")
//    List<Map<String,Object>> findDetailsWithStatus();


   @Query(value="select Id,name from Users where empRole=4",nativeQuery = true)
    List<Map<String,Object>> findManagers();


    @Query(value = "SELECT Users.name,appraisal_master.status " +
            "FROM Users " + "JOIN  appraisal_master ON Users.Id = appraisal_master.employee_id " +
                    "WHERE empRole = 1;",nativeQuery = true)
    List<Map<String,Object>> find();


    @Query("select name from Users where email=:email")
    String findByEmail(String email);

    @Query(value="SELECT Users.email,Users.name " +
            "FROM Users " +
            "JOIN appraisal_master ON Users.Id = appraisal_master.employee_id " +
            "WHERE appraisal_master.status = 'Initialized' ", nativeQuery=true)
    List<Map<String,String>> findbystatus();
    @Query(value = "SELECT u.name as employeename, rm.name as managername, u.email AS employee_email, rm.email AS reporting_manager_email, am.status " +
            "FROM appraisal_master am " +
            "JOIN employee e ON am.employee_id = e.Id " +
            "JOIN Users u ON e.user_id = u.Id " +
            "JOIN Users rm ON e.reporting_manager = rm.Id " +
            "WHERE am.Id =:id", nativeQuery = true)
    Map<String,String> findbymanageremployeestatus(Integer id);

}

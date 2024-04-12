package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

}

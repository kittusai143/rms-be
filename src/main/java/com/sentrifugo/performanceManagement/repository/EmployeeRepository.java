package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//    List<Employee> findByBusinessUnitAndDepartment(String businessUnit, String department);
}
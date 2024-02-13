package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long> {

    List<Employees> findByBusinessUnitAndDepartment(String businessUnit, String department);
}
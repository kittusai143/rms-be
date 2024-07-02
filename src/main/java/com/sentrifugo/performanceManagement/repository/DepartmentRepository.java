package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}

package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation,Long> {
    @Query("SELECT p FROM ProjectAllocation p where p.allocProcessId = :id")
    ProjectAllocation findByAllocProcessId(Long id);
}

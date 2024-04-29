package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation,Long> {
    @Query("SELECT p FROM ProjectAllocation p where p.allocProcessId = :id")
    ProjectAllocation findByAllocProcessId(Long id);

    @Query("Select pa from ProjectAllocation pa where pa.resAllocId = :resAllocId And pa.isActive =:b")
    List<ProjectAllocation> findByResourceAllocId(Long resAllocId, boolean b);

}

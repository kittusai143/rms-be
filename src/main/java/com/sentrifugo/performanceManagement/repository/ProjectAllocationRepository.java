package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectAllocationRepository extends JpaRepository<ProjectAllocation,Long> {
    @Query("SELECT p FROM ProjectAllocation p where p.allocProcessId = :id")
    ProjectAllocation findByAllocProcessId(Long id);

    @Query("Select pa from ProjectAllocation pa where pa.resAllocId = :resAllocId And pa.isActive =:b")
    List<ProjectAllocation> findByResourceAllocId(Long resAllocId, boolean b);
    @Query("Select pa from ProjectAllocation pa where pa.resAllocId = :resAllocId")
    List<ProjectAllocation> findAllBYResAllocID(Long resAllocId);

    @Query(value ="SELECT pa.* FROM ProjectAllocation pa WHERE :endDate  BETWEEN pa.startdate AND pa.enddate;",nativeQuery = true)
    List<ProjectAllocation> findAllocationsForMonth(@Param("endDate") Date endDate);

}

package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResourceAllocProcessRepository extends JpaRepository<ResourceAllocProcess, Long> {
    @Query("SELECT rap, ra, u FROM ResourceAllocProcess rap " +
            "JOIN ResourceAllocation ra ON rap.resAllocId = ra.allocationId " +
            "JOIN Users u ON (rap.updatedBy = u.employeeId OR rap.createdBy = u.employeeId) where rap.isActive =:b")
    List<Object[]> getResourceAllocProcessAndUsers(boolean b);

}

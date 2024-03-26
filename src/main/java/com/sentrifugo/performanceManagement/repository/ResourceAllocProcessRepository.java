package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResourceAllocProcessRepository extends JpaRepository<ResourceAllocProcess, Long> {
    @Query("SELECT rap.id AS id, rap.projectCode AS projectCode , rap.processStatus AS ProcessStatus, rap.createdBy AS createdBy, rap.updatedBy AS UpdatedBy, rap.createdDate AS createdDate, rap.updatedDate AS updatedDate,"+
            " ra.allocationId AS ResAlloId, ra.silId AS ResEID, ra.name AS ResourceName, u.name AS PMOname, u.employeeId AS PMOEID FROM ResourceAllocProcess rap " +
            "JOIN ResourceAllocation ra ON rap.resAllocId = ra.allocationId " +
            "JOIN Users u ON (rap.updatedBy = u.employeeId OR rap.createdBy = u.employeeId) where rap.isActive =:b")
    List<Map<String,Object>> getResourceAllocProcessAndUsers(boolean b);

}

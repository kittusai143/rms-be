package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ResourceAllocProcessRepository extends JpaRepository<ResourceAllocProcess, Long> {
    @Query( value = "SELECT rap.* ,createdUser.name AS createdByName, updatedUser.name AS updatedByName, " +
            "ra.silId AS ResEID, ra.name AS ResourceName, pd.ProjectName, pd.ClientName  " +
            "        FROM ResourceAllocProcess rap " +
            "        JOIN resource_allocation ra  ON rap.resAllocId = ra.allocationId" +
            "        JOIN users  createdUser ON rap.createdBy = createdUser.employeeId " +
            "        LEFT JOIN users  updatedUser ON rap.updatedBy = updatedUser.employeeId " +
            "        LEFT JOIN project_data pd on rap.ProjectCode =pd.ProjectCode " +
            "        WHERE rap.isActive = :b", nativeQuery = true )
    List<Map<String, Object>> getResourceAllocProcessAndUsers(boolean b);

    @Query("SELECT rap FROM ResourceAllocProcess rap WHERE DATE(rap.SBendDate) > curdate() AND rap.isActive =:b")
    List<ResourceAllocProcess> getByIsActiveAndEndDate(boolean b);

    @Query(value = "SELECT rap.* FROM ResourceAllocProcess rap WHERE rap.isactive = true AND YEAR(rap.SBenddate) <= YEAR(CURRENT_DATE()) AND MONTH(rap.SBenddate) <= MONTH(CURRENT_DATE()) AND DAY(rap.SBenddate) > DAY(CURRENT_DATE())", nativeQuery = true)
    List<ResourceAllocProcess> findActiveProcessesWithFutureEndDate();

    @Query(value = "Select rap.* from ResourceAllocProcess rap Where rap.ResAllocID =:resAllocId AND rap.isactive=:b AND rap.ProcessStatus =:status",nativeQuery = true)
    List<ResourceAllocProcess> getByAllocaIDAndISActiveAndStatus(Long resAllocId, boolean b, String status);
}

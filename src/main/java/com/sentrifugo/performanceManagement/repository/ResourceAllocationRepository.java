package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.ResourceAllocProcessService;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocation,Long>, JpaSpecificationExecutor<ResourceAllocation> {
  @NotNull Page<ResourceAllocation> findAll(@Nullable Specification<ResourceAllocation> spec, @NonNull Pageable pageable);

  @Query("SELECT DISTINCT ra.location FROM ResourceAllocation ra")
  List<String> getDistinctLocations();
  @Query("SELECT DISTINCT ra.role FROM ResourceAllocation ra")
  List<String> getDistinctRoles();

  @Query(value = "SELECT ra.allocationId, GROUP_CONCAT(rap.Id) AS processes " +
          "FROM resource_allocation ra " +
          "LEFT JOIN ResourceAllocProcess rap ON ra.AllocationId = rap.ResAllocID AND rap.IsActive = :isActive " +
          "WHERE ra.Status != :status "+
          "GROUP BY ra.allocationId", nativeQuery = true)
  List<Object[]> findResourcesWithActiveProcesses(@Param("isActive") boolean isActive, @Param("status") String status);

  @Query(value = "SELECT ra.allocationId, GROUP_CONCAT(rap.Id) AS processes " +
          "FROM resource_allocation ra " +
          "LEFT JOIN ResourceAllocProcess rap ON ra.AllocationId = rap.ResAllocID AND rap.IsActive = :isActive " +
          "WHERE ra.Status != :status "+
          "GROUP BY ra.allocationId having ra.AllocationId =:id", nativeQuery = true)
  Map<String,?> findByIdWithProcesses(@Param("isActive") boolean isActive, @Param("id") long id, @Param("status") String status);

  @Query("SELECT r FROM ResourceAllocation r WHERE r.silId =:silId")
  ResourceAllocation findBySilId(String silId);

}

package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocation,Long>, JpaSpecificationExecutor<ResourceAllocation> {
  @NotNull Page<ResourceAllocation> findAll(@Nullable Specification<ResourceAllocation> spec, @NonNull Pageable pageable);

  @Query("SELECT DISTINCT ra.location FROM ResourceAllocation ra")
  List<String> getDistinctLocations();
  @Query("SELECT DISTINCT ra.role FROM ResourceAllocation ra")
  List<String> getDistinctRoles();
}

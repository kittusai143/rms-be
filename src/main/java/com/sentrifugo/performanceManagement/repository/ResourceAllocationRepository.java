package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;


@Repository
public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocation,Long>, JpaSpecificationExecutor<ResourceAllocation> {
  @NotNull Page<ResourceAllocation> findAll(@Nullable Specification<ResourceAllocation> spec, @NonNull Pageable pageable);

}

package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.DomainMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainMasterRepository extends JpaRepository<DomainMaster, Integer> {
}

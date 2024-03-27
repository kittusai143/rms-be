package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.TechnologyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyMasterRepository extends JpaRepository<TechnologyMaster, Integer> {

}

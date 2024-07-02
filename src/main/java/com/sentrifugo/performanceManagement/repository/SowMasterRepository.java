package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.SowMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SowMasterRepository extends JpaRepository<SowMaster,Integer> {
    @Query(value = "SELECT sm.SowId FROM pmodashboard.sow_master sm", nativeQuery = true)
    List<String> getSowIds();

    @Query(value = "SELECT * FROM pmodashboard.sow_master sm WHERE sm.SowId = :id", nativeQuery = true)
    SowMaster getSowDataById(String id);
}

package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalConfigRepository extends JpaRepository<AppraisalConfig, Long>{


    @Query("Select parameter from AppraisalConfig where appraisalConfigId=:id ")
    Integer getpid(Integer id);
}
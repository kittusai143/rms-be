package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalMasterRepository extends JpaRepository<AppraisalMaster, Long> {

}

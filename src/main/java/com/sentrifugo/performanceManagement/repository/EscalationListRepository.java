package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EscalationList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscalationListRepository extends JpaRepository<EscalationList,Long> {
    List<EscalationList> findBystatus(String status);

    List<EscalationList> findBydepartment(String department);

    List<EscalationList> findBydesignation(String designation);
}

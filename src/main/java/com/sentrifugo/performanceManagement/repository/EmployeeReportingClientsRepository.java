package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EmployeeReportingClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReportingClientsRepository extends JpaRepository<EmployeeReportingClients,Long> {
}

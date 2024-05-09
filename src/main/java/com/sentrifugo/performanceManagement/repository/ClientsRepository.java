package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ClientsRepository extends JpaRepository<Clients,Long> {

    @Query("SELECT c.clientCode as clientCode, c.clientName as clientName FROM Clients c WHERE c.status = :status GROUP BY c.clientCode, c.clientName")
    List<Map<String, ?>> findAllDistinct(String status);

}

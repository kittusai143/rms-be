package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients,Long> {

}

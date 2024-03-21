package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.ClientInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInformationRepository extends JpaRepository<ClientInformation,Long> {
}

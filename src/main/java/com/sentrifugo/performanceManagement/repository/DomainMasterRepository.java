package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.DomainMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomainMasterRepository extends JpaRepository<DomainMaster, Integer> {
    @Query(value = "select distinct subDomainId from DomainMaster where domainName=:domain")
    List<String> findByDomainName(String domain);



    @Query(value = "select distinct domainName from DomainMaster")
    List<String> getAllDomainNames();
}

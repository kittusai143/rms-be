package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.TechnologyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyDataRepository extends JpaRepository<TechnologyData, Integer> {

}

package com.sentrifugo.performanceManagement.repository;


import com.sentrifugo.performanceManagement.entity.ConsultantData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<ConsultantData,Integer> {
}

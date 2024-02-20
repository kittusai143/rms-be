package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Config;
import com.sentrifugo.performanceManagement.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConfigRepo extends JpaRepository<Config,Integer> {

    @Query("SELECT c FROM Config c WHERE c.id = :id")
    List<Config> findAllById(@Param("id") Integer id);
}

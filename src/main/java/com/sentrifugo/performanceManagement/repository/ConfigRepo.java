package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepo extends JpaRepository<Config,Integer> {

}

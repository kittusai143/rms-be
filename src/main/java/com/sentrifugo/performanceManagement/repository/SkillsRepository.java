package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SkillsRepository extends JpaRepository<Skills,Integer> {

}

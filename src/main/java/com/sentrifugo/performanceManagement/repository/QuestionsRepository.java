package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions,Integer> {

    List<Questions> findByConfigId(Integer id);

    List<Questions> findByIdAndConfigId(Integer questionId, Integer id);

}

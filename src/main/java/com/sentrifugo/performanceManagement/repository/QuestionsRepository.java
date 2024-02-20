package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions,Integer> {

    @Query("SELECT q FROM Questions q WHERE q.configId = :id")
    List<Questions> getQuestionsByConfigId(Integer id);

}

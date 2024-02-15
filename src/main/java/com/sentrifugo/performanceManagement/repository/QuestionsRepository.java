package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Questions,Integer> {
}

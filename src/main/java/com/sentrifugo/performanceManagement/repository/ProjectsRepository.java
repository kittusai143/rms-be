package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects,Long> {
}
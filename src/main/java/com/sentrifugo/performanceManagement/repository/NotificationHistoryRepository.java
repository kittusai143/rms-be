package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    @Query("SELECT nh FROM NotificationHistory nh WHERE nh.resAllocId = :id")
    List<NotificationHistory> getByResAllocID(Long id);
}

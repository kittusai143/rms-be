package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    @Query(value = "SELECT nh.*, u.name as createdName FROM notificationHistory_test nh join users u on nh.CreatedBy  = u.employeeId  and nh.ResAllocID  = :id",nativeQuery = true)
    List<Map<String,Object>> getByResAllocID(Long id);
    @Query(value="SELECT u.name , n.* from users u Join notificationHistory_test n on n.silId=u.employeeId",nativeQuery=true)
    List<Map<String,Object>> getNotifications();
}

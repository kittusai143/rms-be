package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import com.sentrifugo.performanceManagement.repository.NotificationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationHistoryService {

    @Autowired
    public NotificationHistoryRepository notificationHIstoryRepository;


    public List<NotificationHistory> getByResAllocID(Long id) {
        return notificationHIstoryRepository.getByResAllocID(id);
    }

    public NotificationHistory createNotification(NotificationHistory notification) {
        return notificationHIstoryRepository.save(notification);
    }
}

package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import com.sentrifugo.performanceManagement.service.NotificationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("notificationHistory")
public class NotificationHistoryController {
    @Autowired
    public NotificationHistoryService notificationHistoryService;
    @GetMapping("/byResAllocid/{id}")
    public List<Map<String,Object>> getByID(@PathVariable Long id){
        return notificationHistoryService.getByResAllocID(id);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotifications() {

        List<Map<String,Object>> data= notificationHistoryService.getNotifications();
        if(data.isEmpty())
            return ResponseEntity.ok("No DATa found");
        else
            return ResponseEntity.ok(data);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody Map<String, ?> request) {
        try {
            NotificationHistory notification = new NotificationHistory();
            notification.setSilId((String) request.get("silId"));
            notification.setResAllocId(((Integer) request.get("resAllocId")).longValue());
            notification.setCreatedBy((String) request.get("createdBy"));
            notification.setCreatedDate(new Date(System.currentTimeMillis())); // Assuming current date/time
            notification.setComment((String) request.get("comment"));

            notificationHistoryService.createNotification(notification);

            return ResponseEntity.ok("Notification created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create notification: " + e.getMessage());
        }
    }


}

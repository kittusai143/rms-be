package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResourceAllocProcessService {

    @Autowired
    private ResourceAllocProcessRepository resourceAllocProcessRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ResourceAllocationService resourceAllocationService;

    public List<Map<String,Object>> getResourceAllocProcessAndUsers() {
        return resourceAllocProcessRepository.getResourceAllocProcessAndUsers(true);
    }

    public ResourceAllocProcess getById(long id) {
        return resourceAllocProcessRepository.findById(id).orElse(null);
    }

    public ResourceAllocProcess createResourceAllocProcess(ResourceAllocProcess resourceAllocProcess) {
        ResourceAllocProcess updated = resourceAllocProcessRepository.save(resourceAllocProcess);
        NotificationHistory notification = new NotificationHistory();
        notification.setSilId( updated.getSilId());
        notification.setResAllocId( updated.getResAllocId());
        notification.setCreatedBy( updated.getUpdatedBy() );
        notification.setCreatedDate(new java.util.Date(System.currentTimeMillis()));
        notification.setComment(usersService.getbyEmployeeID(updated.getUpdatedBy())+" "+ updated.getProcessStatus() +" "+resourceAllocationService.getById(updated.getResAllocId()).getName());

        return updated;
    }

    public ResourceAllocProcess updateStatus(Long id, Map<String, ?> requestBody) {
        Optional<ResourceAllocProcess> optionalAllocation = resourceAllocProcessRepository.findById(id);
        if (optionalAllocation.isPresent()) {
            ResourceAllocProcess allocation = optionalAllocation.get();
            allocation.setProcessStatus((String) requestBody.get("processStatus"));
            allocation.setUpdatedBy((String) requestBody.get("updatedBy"));
            allocation.setUpdatedDate(new Date(System.currentTimeMillis()));
            ResourceAllocProcess updated = resourceAllocProcessRepository.save(allocation);

            NotificationHistory notification = new NotificationHistory();
            notification.setSilId( updated.getSilId());
            notification.setResAllocId( updated.getResAllocId());
            notification.setCreatedBy( updated.getUpdatedBy() );
            notification.setCreatedDate(new java.util.Date(System.currentTimeMillis()));
            notification.setComment(usersService.getbyEmployeeID(updated.getUpdatedBy())+" "+ updated.getProcessStatus() +" "+resourceAllocationService.getById(updated.getResAllocId()).getName());

            return updated;
        } else {
            return null;
        }
    }
}

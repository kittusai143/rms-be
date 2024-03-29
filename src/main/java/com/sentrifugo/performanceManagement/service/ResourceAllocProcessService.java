package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        notification.setCreatedBy( updated.getCreatedBy() );
        notification.setCreatedDate(new java.util.Date(System.currentTimeMillis()));
        notification.setComment(usersService.getbyEmployeeID(updated.getCreatedBy())+" "+ updated.getProcessStatus() +" "+resourceAllocationService.getById(updated.getResAllocId()).getName());

        return updated;
    }

    public ResourceAllocProcess updateStatus(Long id, Map<String, ?> requestBody) throws ParseException {
        Optional<ResourceAllocProcess> optionalAllocation = resourceAllocProcessRepository.findById(id);
        if (optionalAllocation.isPresent()) {
            ResourceAllocProcess allocation = optionalAllocation.get();
            allocation.setProcessStatus((String) requestBody.get("processStatus"));
            allocation.setUpdatedBy((String) requestBody.get("updatedBy"));
            allocation.setUpdatedDate(new Date(System.currentTimeMillis()));
            if((String) requestBody.get("startDate")!=null && (String) requestBody.get("endDate") !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                java.util.Date startDate = sdf.parse((String) requestBody.get("startDate"));
                java.util.Date endDate = sdf.parse((String) requestBody.get("endDate"));
                allocation.setStartDate(startDate);
                allocation.setEndDate(endDate);
            }

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

    public List<ResourceAllocProcess> getAll() {
        return resourceAllocProcessRepository.findAll();
    }
}

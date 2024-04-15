package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.repository.NotificationHistoryRepository;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ResourceAllocProcessService {

    @Autowired
    private ResourceAllocProcessRepository resourceAllocProcessRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;
    @Autowired
    private ResourceAllocationService resourceAllocationService;
    @Autowired
    private ProjectAllocationService projectAllocationService;
    @Autowired
    private ProjectAllocationRepository projectAllocationRepository;

    public List<ResourceAllocProcess> getAll() {
        return resourceAllocProcessRepository.findAll();
    }

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
        notification.setComment(updated.getProcessStatus());
        notificationHistoryRepository.save(notification);
        return updated;
    }

    public ResourceAllocProcess updateStatus(Long id, Map<String, ?> requestBody) throws ParseException {
        Optional<ResourceAllocProcess> optionalAllocation = resourceAllocProcessRepository.findById(id);
        java.util.Date startDate;
        java.util.Date endDate;
        if (optionalAllocation.isPresent()) {
            ResourceAllocProcess allocation = optionalAllocation.get();
            allocation.setProjectCode((String) requestBody.get("projectCode"));
            allocation.setProcessStatus((String) requestBody.get("processStatus"));
            allocation.setUpdatedBy((String) requestBody.get("updatedBy"));
            allocation.setUpdatedDate(new Date(System.currentTimeMillis()));
            if((String) requestBody.get("startDate")!=null && (String) requestBody.get("endDate") !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse((String) requestBody.get("startDate"));
                endDate = sdf.parse((String) requestBody.get("endDate"));
                if(allocation.getSBstartDate()!=null && allocation.getSBstartDate()!=null){
                    allocation.setAllocStartDate(startDate);
                    allocation.setAllocEndDate(endDate);
                }else{
                    allocation.setSBstartDate(startDate);
                    allocation.setSBendDate(endDate);
                }
            }
            ResourceAllocProcess updated = resourceAllocProcessRepository.save(allocation);
            if(Objects.equals(updated.getProcessStatus(), "Allocated")){
                ProjectAllocation projectAllocation = new ProjectAllocation();
                projectAllocation.setAllocProcessId(updated.getId());
                projectAllocation.setResAllocId(updated.getResAllocId());
                projectAllocation.setProjectCode(updated.getProjectCode());
                projectAllocation.setCreatedBy(updated.getUpdatedBy());
                projectAllocation.setCreatedDate(updated.getUpdatedDate());
                projectAllocation.setStartDate(updated.getAllocStartDate());
                projectAllocation.setEndDate(updated.getAllocEndDate());
                projectAllocation.setActive(true);
                projectAllocationService.createProjectAllocation(projectAllocation);
            }
            if(Objects.equals(updated.getProcessStatus(), "Deallocated")){
                ProjectAllocation projectAllocation = projectAllocationService.findByProcessId(updated.getId());
                projectAllocation.setCreatedBy(updated.getUpdatedBy());
                projectAllocation.setCreatedDate(updated.getUpdatedDate());
                projectAllocation.setActive(false);
                projectAllocationService.updateProjectAllocation(projectAllocation);
            }

            NotificationHistory notification = new NotificationHistory();
            notification.setSilId( updated.getSilId());
            notification.setResAllocId( updated.getResAllocId());
            notification.setCreatedBy( updated.getUpdatedBy() );
            notification.setCreatedDate(new java.util.Date(System.currentTimeMillis()));
            notification.setComment(updated.getProcessStatus());
            notificationHistoryRepository.save(notification);
            return updated;
        } else {
            return null;
        }
    }

    @Scheduled(cron = "@daily")
    public void getResourceAllocProcessesWithActiveStatusAndFutureEndDate() {
        List<ResourceAllocProcess> processes = resourceAllocProcessRepository.findActiveProcessesWithFutureEndDate();
        System.out.println(processes);
        for(ResourceAllocProcess process: processes){
            ResourceAllocProcess prop = process;
            prop.setActive(false);
            ResourceAllocProcess r = resourceAllocProcessRepository.save(prop);
            System.out.println(r);
        }
    }

    public void deleteProcess(Long id) {
         resourceAllocProcessRepository.deleteById(id);
    }
}

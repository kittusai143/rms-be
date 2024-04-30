package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.*;
import com.sentrifugo.performanceManagement.repository.NotificationHistoryRepository;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
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
    @Autowired
    private ResourceAllocationRepository resourceAllocationRepository;
    @Autowired
    private ProjectsService projectsService;

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
            if( (String) requestBody.get("projectCode")!=null){
                allocation.setProjectCode((String) requestBody.get("projectCode"));
            }
            if( (String) requestBody.get("projectId")!=null){
                allocation.setProjectId( Long.parseLong((String) requestBody.get("projectId")) );
            }
            allocation.setProcessStatus((String) requestBody.get("processStatus"));
            allocation.setUpdatedBy((String) requestBody.get("updatedBy"));
            allocation.setUpdatedDate(new Date(System.currentTimeMillis()));
            allocation.setReadStatus(false);
            if( (String) requestBody.get("feedback")!=null){
                allocation.setFeedback( (String) requestBody.get("feedback"));
            }
            if((String) requestBody.get("startDate")!=null && (String) requestBody.get("endDate") !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.parse((String) requestBody.get("startDate"));
                endDate = sdf.parse((String) requestBody.get("endDate"));
                if(allocation.getSBstartDate()!=null && allocation.getSBendDate()!=null && allocation.getProcessStatus().equals("Allocation Requested")){
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

                //Check if resource is in any other projects in project allocation table
                List<ProjectAllocation> projectAllocations = projectAllocationService.getByResourceAllocationId(updated.getResAllocId());
                System.out.println("..................");
                System.out.println(projectAllocations);
                if(projectAllocations.isEmpty()){
                    Projects project = projectsService.getProjectById(updated.getProjectId());
                    System.out.println("..................Inside");
                    ResourceAllocation resourceAllocation = resourceAllocationService.getById(updated.getResAllocId()).getResource();
                    resourceAllocation.setAllocationStatus("Allocated");
                    resourceAllocation.setSowID(project.getSowId());
                    resourceAllocation.setClientCode(project.getClientCode());
                    resourceAllocation.setProjectCode(project.getProjectCode());
//                  resourceAllocation.setClientTimesheetAccess(.....);
//                  resourceAllocation.setPartnerEmailID(.....);
//                  resourceAllocation.setClientEmailID(........);
                    resourceAllocationRepository.save(resourceAllocation);
                }
                projectAllocationService.createProjectAllocation(projectAllocation);
            }
            if(Objects.equals(updated.getProcessStatus(), "Deallocated")){
                Optional<ResourceAllocProcess> resourceAllocProcess=resourceAllocProcessRepository.findById(updated.getId());
                if (resourceAllocProcess.isPresent()) {
                    ResourceAllocProcess allocations = optionalAllocation.get();
                    allocations.setActive(false);
                }
                ProjectAllocation projectAllocation = projectAllocationService.findByProcessId(updated.getId());
                projectAllocation.setCreatedBy(updated.getUpdatedBy());
                projectAllocation.setCreatedDate(updated.getUpdatedDate());
                projectAllocation.setActive(false);
                projectAllocationService.updateProjectAllocation(projectAllocation);

                //Check if resource is in any other projects in project allocation table
                List<ProjectAllocation> projectAllocations = projectAllocationService.getByResourceAllocationId(updated.getResAllocId());
                if(projectAllocations.isEmpty()){
                    Projects project = projectsService.getProjectById( updated.getProjectId());

                    ResourceAllocation resourceAllocation = resourceAllocationService.getById(updated.getResAllocId()).getResource();
                    resourceAllocation.setAllocationStatus("Available");

                    resourceAllocationRepository.save(resourceAllocation);
                }
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

    public void markProcessAsRead(ResourceAllocProcess process) {
        process.setReadStatus(true);
        if(process.getProcessStatus().equals("Deallocated")){
            process.setActive(false);
        }
        resourceAllocProcessRepository.save(process);
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

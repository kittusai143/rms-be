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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    public ResponseEntity<?> updateStatus(Long id, Map<String, ?> requestBody) throws ParseException {
        Optional<ResourceAllocProcess> optionalAllocation = resourceAllocProcessRepository.findById(id);
        java.util.Date startDate;
        java.util.Date endDate;
        if (optionalAllocation.isPresent()) {
            ResourceAllocProcess allocation = optionalAllocation.get();
            if(((String) requestBody.get("processStatus")).equals("SoftBlock Requested") || ((String) requestBody.get("processStatus")).equals("SoftBlocked")){
                List<ResourceAllocProcess> processes = resourceAllocProcessRepository.getByAllocaIDAndISActiveAndStatus(allocation.getResAllocId(),true,"SoftBlocked");
                if(processes.stream().count()>=2){
                    System.out.println("No more softBlocks Accepted");
                    return ResponseEntity.badRequest().body("No more soft blocks Accepted");
                }
            }
            if(((String) requestBody.get("processStatus")).equals("Allocation Requested") || ((String) requestBody.get("processStatus")).equals("Allocated")){
                List<ResourceAllocProcess> processes = resourceAllocProcessRepository.getByAllocaIDAndISActiveAndStatus(allocation.getResAllocId(),true,"Allocated");
                if(processes.stream().count()>=1){
                    System.out.println("No more Allocations Accepted");
                    return ResponseEntity.badRequest().body("No more allocation requests Accepted");
                }
            }
            if( (String) requestBody.get("projectCode")!=null){
                allocation.setProjectCode((String) requestBody.get("projectCode"));
            }
            if( (String) requestBody.get("projectId")!=null){
                allocation.setProjectId( Long.parseLong((String) requestBody.get("projectId")) );
            }
            if( (String) requestBody.get("requirementId")!=null){
                allocation.setRequirementID( (String) requestBody.get("requirementId") );
            }
            if( (String) requestBody.get("deAllocReason")!=null){
                allocation.setDeAllocReason( (String) requestBody.get("deAllocReason") );
            }
            allocation.setProcessStatus((String) requestBody.get("processStatus"));
            allocation.setUpdatedBy((String) requestBody.get("updatedBy"));
            allocation.setUpdatedDate(new Date(System.currentTimeMillis()));
            allocation.setPmReadStatus(false);
            allocation.setRmReadStatus(false);
            allocation.setPMOReadStatus(false);
            if( (String) requestBody.get("feedback")!=null){
                allocation.setFeedback( (String) requestBody.get("feedback"));
            }
            if((String) requestBody.get("startDate")!=null && (String) requestBody.get("endDate") !=null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startdate = LocalDate.parse((String) requestBody.get("startDate"), formatter);
                LocalDate enddate = LocalDate.parse((String) requestBody.get("endDate"), formatter);

                ZoneId utcZone = ZoneId.of("UTC");
                ZonedDateTime startDateZoned = startdate.atStartOfDay(utcZone);
                ZonedDateTime endDateZoned = enddate.atStartOfDay(utcZone);

                startDate = Date.from(startDateZoned.toInstant());
                endDate = Date.from(endDateZoned.toInstant());
                if(allocation.getSBstartDate()!=null && allocation.getSBendDate()!=null && allocation.getProcessStatus().equals("Allocation Requested")){
                    allocation.setAllocStartDate(startDate);
                    allocation.setAllocEndDate(endDate);
                }else{
                    allocation.setSBstartDate(startDate);
                    allocation.setSBendDate(endDate);
                }
            }
            ResourceAllocProcess updated = resourceAllocProcessRepository.save(allocation);
            
            if(Objects.equals(updated.getProcessStatus(), "SoftBlocked")){
                List<ResourceAllocProcess> processes = resourceAllocProcessRepository.getByAllocaIDAndISActiveAndStatus(updated.getResAllocId(),true,"SoftBlocked");
                if(processes.stream().count()>=2){
                    // Inactive the existing processes when softBlocked by two managers
                    List<ResourceAllocProcess> allProcesses = resourceAllocProcessRepository.getByResourceAllocationIdAndIsActive(updated.getResAllocId(), true);
                    for(ResourceAllocProcess process:allProcesses){
                        if(Objects.equals(process.getProcessStatus(), "SoftBlocked")){
                            continue;
                        }
                        process.setActive(false);
                        process.setUpdatedBy(updated.getUpdatedBy());
                        process.setUpdatedDate(new Date(System.currentTimeMillis()));
                        resourceAllocProcessRepository.save(process);
                    }
                }
            }

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
                if(projectAllocations.isEmpty()){
                    Projects project = projectsService.getProjectById(updated.getProjectId());
                    ResourceAllocation resourceAllocation = resourceAllocationService.getById(updated.getResAllocId()).getResource();
                    resourceAllocation.setAllocationStatus("Allocated");
                    resourceAllocation.setClientCode(project.getClientCode());
                    resourceAllocation.setProjectCode(project.getProjectCode());
                    resourceAllocation.setProjectEndDate(updated.getAllocEndDate());
                    resourceAllocation.setProjectstartDate(updated.getAllocStartDate());
                    resourceAllocation.setProjectType(project.getTypeOfProject());
                    resourceAllocation.setProjectName(project.getProjectName());
//                  resourceAllocation.setClientTimesheetAccess(.....);
//                  resourceAllocation.setPartnerEmailID(.....);
//                  resourceAllocation.setClientEmailID(........);
                    resourceAllocationRepository.save(resourceAllocation);
                }
                projectAllocationService.createProjectAllocation(projectAllocation);

                // Inactive the existing processes on allocating the resource
                List<ResourceAllocProcess> processes = resourceAllocProcessRepository.getByResourceAllocationIdAndIsActive(updated.getResAllocId(), true);
                for(ResourceAllocProcess process:processes){
                    if(process.getId().equals(updated.getId())){
                        continue;
                    }
                    process.setActive(false);
                    process.setUpdatedBy(updated.getUpdatedBy());
                    process.setUpdatedDate(new Date(System.currentTimeMillis()));
                    resourceAllocProcessRepository.save(process);
                }
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

                //Check if resource is in any other projects in project_allocation table
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
            return ResponseEntity.ok().body(updated);
        } else {
            return ResponseEntity.badRequest().body("Invalid id");
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


    public void markProcessAsRead(Long id, String role) {
        ResourceAllocProcess process = resourceAllocProcessRepository.findById(id).get();
        if(role.equals("Manager")){
            process.setPmReadStatus(true);
        }
        if(role.equals("Resource Manager")){
            process.setRmReadStatus(true);
        }
        if(role.equals("PMO Analyst")){
            process.setPMOReadStatus(true);
        }
        if(process.getProcessStatus().equals("Deallocated")){
            process.setActive(false);
        }
        resourceAllocProcessRepository.save(process);
    }
}

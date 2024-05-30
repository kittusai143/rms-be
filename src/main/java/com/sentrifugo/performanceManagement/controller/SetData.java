package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.NotificationHistory;
import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.repository.NotificationHistoryRepository;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
public class SetData {
    @Autowired
    private ResourceAllocationRepository resourceAllocationRepository;
    @Autowired
    private ResourceAllocProcessRepository resourceAllocProcessRepository;
    @Autowired
    private ResourceAllocationService resourceAllocationService;
    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    private ProjectAllocationRepository projectAllocationRepository;
    @GetMapping("/arrange")
    public ResponseEntity<?> setData(){
        try {
            ResourceAllocFilters filters = new ResourceAllocFilters();
            List<String> status = new ArrayList<>();
            status.add("Allocated");
            filters.setAvailability(status);
            List<Resources> resources = resourceAllocationService.filterResourceAllocations(filters);
            for(Resources resource: resources){

                if(resource.getResource().getAllocationStatus().equals("Allocated")){
                    System.out.println(resource);
                    ResourceAllocProcess process = new ResourceAllocProcess();
                    process.setSilId(resource.getResource().getSilId());
                    process.setResAllocId(resource.getResource().getAllocationId().longValue());
                    process.setProjectCode(resource.getResource().getProjectCode());
                    process.setProcessStatus("Allocated");
                    process.setAllocStartDate(resource.getResource().getProjectstartDate());
                    process.setAllocEndDate(resource.getResource().getProjectEndDate());
                    process.setBillability(resource.getResource().getBillability());
                    process.setCreatedBy("01");
                    process.setCreatedDate(new Date(System.currentTimeMillis()));
                    process.setActive(true);
                    process.setRmReadStatus(true);
                    process.setPMOReadStatus(true);
                    process.setPmReadStatus(true);
                    ResourceAllocProcess createdprocess = resourceAllocProcessRepository.save(process);

                    ProjectAllocation projectAllocation = new ProjectAllocation();
                    projectAllocation.setActive(true);
                    projectAllocation.setProjectCode(resource.getResource().getProjectCode());
                    projectAllocation.setResAllocId(resource.getResource().getAllocationId().longValue());
                    projectAllocation.setStartDate(resource.getResource().getProjectstartDate());
                    projectAllocation.setEndDate(resource.getResource().getProjectEndDate());
                    projectAllocation.setAllocProcessId(createdprocess.getId());
                    projectAllocation.setCreatedBy("01");
                    projectAllocation.setCreatedDate(new Date(System.currentTimeMillis()));

                    projectAllocationRepository.save(projectAllocation);

                    NotificationHistory notificationHistory = new NotificationHistory();
                    notificationHistory.setSilId(resource.getResource().getSilId());
                    notificationHistory.setResAllocId(resource.getResource().getAllocationId().longValue());
                    notificationHistory.setCreatedBy("01");
                    notificationHistory.setCreatedDate(new Date(System.currentTimeMillis()));
                    notificationHistory.setComment("Allocated");
                    notificationHistoryRepository.save(notificationHistory);

                }
            }
            return ResponseEntity.ok().body("DONE");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.ResourceAllocSpecification;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ResourceAllocationService {

    @Autowired
    public ResourceAllocationRepository resourceAllocationRepository;
    @Autowired
    public ResourceAllocProcessRepository resourceAllocProcessRepository;
    @Autowired
    public ProjectAllocationRepository projectAllocationRepository;

    public List<Resources> filterResourceAllocations(ResourceAllocFilters criteria) {
        Specification<ResourceAllocation> spec = ResourceAllocSpecification.filterResourceAllocations(criteria);
        List<ResourceAllocation> resource = resourceAllocationRepository.findAll(spec);
        List<Resources> allResources = getAllResourceAllocations();
        List<Resources> response = new ArrayList<Resources>();
        for (ResourceAllocation filtered: resource){
            for (Resources res: allResources){
                if( res.getResource().getAllocationId().equals(filtered.getAllocationId()) ){
                    response.add( res );
                }
            }
        }
        return response;
    }

    public List<Resources> getAllResourceAllocations() {
        List<Object[]> result = resourceAllocationRepository.findResourcesWithActiveProcesses(true, "Inactive");
        List<Resources> resourcesList = new ArrayList<>();

        for (Object[] row : result) {
            Long resourceId = ((Number) row[0]).longValue();
            ResourceAllocation resourceAllocation = resourceAllocationRepository.findById(resourceId).orElse(null);

            if (resourceAllocation == null) {continue;}

            String processConcatenated = (String) row[1];
            List<ResourceAllocProcess> processes = parseProcesses(processConcatenated);

            Resources resources = new Resources();
            resources.setResource(resourceAllocation);
            resources.setProcesses(processes);

            resourcesList.add(resources);
        }
        return resourcesList;
    }

//    public List<Resources> getAllResourceAllocations(List<String> availability) {
//        List<Object[]> result = resourceAllocationRepository.findResourcesWithActiveProcesses(true, "Active");
//        List<Resources> resourcesList = new ArrayList<>();
//
//        for (Object[] row : result) {
//            Long resourceId = ((Number) row[0]).longValue();
//            ResourceAllocation resourceAllocation = resourceAllocationRepository.findById(resourceId).orElse(null);
//
//            if (resourceAllocation == null) {continue;}
//
//            String processConcatenated = (String) row[1];
//            List<ResourceAllocProcess> processes = parseProcesses(processConcatenated);
//            boolean allocated= false;
//            if(availability == null ||  availability.isEmpty() || (availability.contains("Available") && !availability.contains("Allocated")) ){
//                for(ResourceAllocProcess process : processes){
//                    if (Objects.equals(process.getProcessStatus(), "Allocated")) {
//                        allocated = true;
//                        break;
//                    }
//                }
//            }
//
//            if(!allocated){
//                Resources resources = new Resources();
//                resources.setResource(resourceAllocation);
//                resources.setProcesses(processes);
//                resourcesList.add(resources);
//            }else{
//                 continue;
//            }
//        }
//        return resourcesList;
//    }

    private List<ResourceAllocProcess> parseProcesses(String processConcatenated) {
        List<ResourceAllocProcess> processes = new ArrayList<>();
        if (processConcatenated != null && !processConcatenated.isEmpty()) {
            String[] processIds = processConcatenated.split(",");
            for (String id : processIds) {
                Optional<ResourceAllocProcess> OptionalProcess = resourceAllocProcessRepository.findById(Long.parseLong(id));
                if(OptionalProcess.isPresent()){
                    ResourceAllocProcess process = OptionalProcess.get();
                    processes.add(process);
                }
            }
        }
        return processes;
    }

    public Resources getById(Long id){
        Map<String, ?> result = resourceAllocationRepository.findByIdWithProcesses(true, id, "Inactive" );
        Resources resources = new Resources();
        System.out.println(result.get("allocationId"));
        resources.setResource(resourceAllocationRepository.findById(((Number) result.get("allocationId")).longValue()).orElse(null));
        if (resources.getResource() == null) {return null;}
        String processConcatenated = (String) result.get("processes");
        List<ResourceAllocProcess> processes = parseProcesses(processConcatenated);
        resources.setProcesses(processes);
        return resources;
    }

    public ResourceAllocation updateResourceAllocation( ResourceAllocation updatedAllocation) {
            return resourceAllocationRepository.save(updatedAllocation);
    }

    public List<String> getDistinctLocations() {
        return resourceAllocationRepository.getDistinctLocations();
    }

    public List<String> getDistinctRoles() {
        return resourceAllocationRepository.getDistinctRoles();
    }

    public void addResourceAllocation(ResourceAllocation resourceAllocation) {
        resourceAllocationRepository.save(resourceAllocation);
    }

    public ResponseEntity<?> updateStatus(Long id, Map<String,?> requestBody) {
        ResourceAllocation resourceAllocation = resourceAllocationRepository.findById(id).orElse(null);
        if(resourceAllocation != null){
            resourceAllocation.setStatus((String) requestBody.get("status"));
            return ResponseEntity.ok().body(resourceAllocationRepository.save(resourceAllocation));
        }else {
            return ResponseEntity.badRequest().body("Incorrect Resource ID");
        }
    }

    public ResourceAllocation getBySilId(String silId){
        return resourceAllocationRepository.findBySilId(silId);
    }

    public Map<String, Long> getBenchCountByMonth() {
        Map<String, Long> benchCountMap = new LinkedHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11); // Start from 11 months ago

        for (int i = 0; i < 12; i++) {
            Date startDate = getFirstDayOfMonth(calendar.getTime());
            Date endDate = getLastDayOfMonth(calendar.getTime());

            List<ProjectAllocation> projectAllocations = projectAllocationRepository.findAllocationsForMonth( endDate);

            // Calculate bench count for the month
            long totalResources = resourceAllocationRepository.findAll().stream().count();
            long allocatedResources = projectAllocations.size();
            long benchCount = totalResources - allocatedResources;
//            System.out.println(totalResources+ " "+allocatedResources+ " "+benchCount);
            String monthYearKey = dateFormat.format(startDate);
            benchCountMap.put(monthYearKey, benchCount);
//            System.out.println(benchCountMap);
            calendar.add(Calendar.MONTH, 1); // Move to next month
        }

        return benchCountMap;
    }

    // Method to get the first day of a month
    private Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    // Method to get the last day of a month
    private Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

}

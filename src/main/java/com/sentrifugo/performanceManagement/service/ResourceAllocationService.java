package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.ResourceAllocSpecification;
import com.sentrifugo.performanceManagement.vo.ResourceAllocationDTO;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<ResourceAllocation> pdsgetAllResourceAllocations(){
        return resourceAllocationRepository.findAll();
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
//        System.out.println(result.get("allocationId"));
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11); // Start from 11 months ago

        for (int i = 0; i < 12; i++) {
            // Get the first and last day of the current month
            Date startDate = getFirstDayOfMonth(calendar.getTime());
            Date endDate = getLastDayOfMonth(calendar.getTime());

            List<ProjectAllocation> projectAllocations = projectAllocationRepository.findAllocationsForMonth(endDate);
            List<ResourceAllocation> resources = resourceAllocationRepository.findActiveResources("Inactive", endDate);

            // Extract the allocation IDs from project allocations
            Set<Long> allocatedResourceIds = projectAllocations.stream()
                    .map(ProjectAllocation::getResAllocationId)
                    .collect(Collectors.toSet());

            // Filter out the bench resources (resources not in the allocatedResourceIds)
            List<ResourceAllocation> benchResources = resources.stream()
                    .filter(resource -> !allocatedResourceIds.contains(resource.getAllocationIddd()))
                    .collect(Collectors.toList());

            // Calculate bench count for the month
            long totalResources = resources.size();
            long allocatedResources = projectAllocations.size();
            long benchCount = totalResources - allocatedResources;
            // Debugging information
            System.out.println("i = " + i);
            System.out.println("startDate = " + startDate);
            System.out.println("endDate = " + endDate);
            System.out.println("totalResources = " + totalResources);
            System.out.println("allocatedResources = " + allocatedResources);
            System.out.println("benchCount = " + benchCount);
            System.out.println(benchResources);

            String monthYearKey = dateFormat.format(startDate);
            benchCountMap.put(monthYearKey, benchCount);

            calendar.add(Calendar.MONTH, 1); // Move to the next month
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

    public Object add(ResourceAllocation resourceAllocation) {
        return  resourceAllocationRepository.save(resourceAllocation);
    }

    public Map<String,Long> getCounts()
    {
        Long a= resourceAllocationRepository.getBillable();
        Long b= resourceAllocationRepository.getNonBillable();
        Long c=resourceAllocationRepository.getOffSite();
        Long d= resourceAllocationRepository.getOnSite();
        Long e= resourceAllocationRepository.getFTE();
        Long f= resourceAllocationRepository.getConsult();
        Long g= resourceAllocationRepository.getM();
        Long i= resourceAllocationRepository.getF();
        Long j= resourceAllocationRepository.getHybrid();
        Long k=resourceAllocationRepository.getRemote();
        Map<String,Long> map=new HashMap<>();
        map.put("Billable",a);
        map.put("NonBillable",b);
        map.put("OffSite",c);
        map.put("OnSite",d);
        map.put("FullTime",e);
        map.put("Consultant",f);
        map.put("Male",g);
        map.put("Female",i);
        map.put("Hybrid",j);
        map.put("Remote",k);
        return map;
    }



    public Integer getEnding(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return resourceAllocationRepository.getEnding(startDate,endDate);
    }

//    public ResourceAllocation updateResourceAllocation(Long id, Map<String,?> updatedAllocation) {
//        Optional<ResourceAllocation> optionalAllocation = resourceAllocationRepository.findById(id);
//        if (optionalAllocation.isPresent()) {
//            ResourceAllocation allocation = optionalAllocation.get();
//
//            allocation.setRole(updatedAllocation.getRole());
//            allocation.setEmployeeType(updatedAllocation.getEmployeeType());
//            allocation.setDoj(updatedAllocation.getDoj());
//            allocation.setStatus(updatedAllocation.isStatus());
//            allocation.setPartner(updatedAllocation.getPartner());
//            allocation.setProjectName(updatedAllocation.getProjectName());
//            allocation.setProjectCode(updatedAllocation.getProjectCode());
//            allocation.setProjectType(updatedAllocation.getProjectType());
//            allocation.setProjectStartDate(updatedAllocation.getProjectStartDate());
//            allocation.setBillingStartDate(updatedAllocation.getBillingStartDate());
//            allocation.setBillingEndDate(updatedAllocation.getBillingEndDate());
//            allocation.setProjectEndDate(updatedAllocation.getProjectEndDate());
//            allocation.setSow(updatedAllocation.getSow());
//            allocation.setSowStartDate(updatedAllocation.getSowStartDate());
//            allocation.setSowEndDate(updatedAllocation.getSowEndDate());
//            allocation.setClientManager(updatedAllocation.getClientManager());
//            allocation.setBillability(updatedAllocation.getBillability());
//            allocation.setLocation(updatedAllocation.getLocation());
//            allocation.setClientTimesheetAccess(updatedAllocation.getClientTimesheetAccess());
//            allocation.setPartnerEmailID(updatedAllocation.getPartnerEmailID());
//            allocation.setClientEmailID(updatedAllocation.getClientEmailID());
//            allocation.setYubikey(updatedAllocation.getYubikey());
//            allocation.setYubikeySno(updatedAllocation.getYubikeySno());
//            allocation.setContactNumber(updatedAllocation.getContactNumber());
//            allocation.setGender(updatedAllocation.getGender());
//            allocation.setSkillset1(updatedAllocation.getSkillset1());
//            allocation.setSkillset2(updatedAllocation.getSkillset2());
//            allocation.setTraining(updatedAllocation.getTraining());
//            allocation.setCertifications(updatedAllocation.getCertifications());
//            allocation.setTechnologyDivision(updatedAllocation.getTechnologyDivision());
//            allocation.setAwards(updatedAllocation.getAwards());
//            allocation.setAudit(updatedAllocation.getAudit());
//
//            return resourceAllocationRepository.save(allocation);
//        } else {
//            return null;
//        }
//    }

    public List<Long> getEmployeesData() {
        return resourceAllocationRepository.getDetails();
    }
    public List<Long> getEmployeesAllocatedData() {
        return resourceAllocationRepository.getEmployeesAllocatedData();
    }

    public List<ResourceAllocation> getResourceDataList(String startDate, String endDate) {
        return resourceAllocationRepository.getResourceDataList(startDate, endDate);
    }
//
//    public ResourceAllocation updateResourceAllocation(ResourceAllocation updatedAllocation) {
//        if(resourceAllocationRepository.existsById(Long.valueOf(updatedAllocation.getAllocationId())))
//            return resourceAllocationRepository.save(updatedAllocation);
//        else
//            return null;
//    }


    public List<Map<String,Integer>> get(String year) {
        return resourceAllocationRepository.getDetailsBasedOnYear(year);
    }

    public List<ResourceAllocation> getDataBasedOnMonthAndYear(String month, String year) {
        return resourceAllocationRepository.getDataBasedOnMonthAndYear(month, year);
    }

    public List<ResourceAllocation> getDataBasedOnYear(String year) {
        return resourceAllocationRepository.getDataBasedOnYear(year);
    }

    public List<ResourceAllocation> getAvailableResources() {
        return resourceAllocationRepository.getAvailableResources();
    }

    public List<ResourceAllocation> getAllocatedResources() {
        return resourceAllocationRepository.getAllocatedResources();
    }

    public List<ResourceAllocationDTO> getDataOfTheYear() {
        return resourceAllocationRepository.getDataOfTheYear();
    }


    public List<ResourceAllocation> getDataForClientName(String clientName, String projectName) {
        List<ResourceAllocation> resourceAllocation = null;

        if(projectName.equals("null")) {
//            System.out.println("Hello");
            resourceAllocation =resourceAllocationRepository.getDataForClientName(clientName);
            return resourceAllocation;
        }
        if(!projectName.equals("null") ){
            resourceAllocation = resourceAllocationRepository.getDataForClientNameAndProjectName(clientName, projectName);
            return resourceAllocation;
        }
        return  resourceAllocation;

    }



    public List<String> getDataForProjects(String clientCode) {
        return resourceAllocationRepository.getDataForProjects(clientCode);
    }

    public List<Map<String, Object>> getNumberOfClients() {
        return resourceAllocationRepository.getNumberOfClients();
    }

    public List<String> getEmployeeId() {
        return resourceAllocationRepository.getEmployeeId();
    }


    public List<ResourceAllocation> getEmployeeResourceData(String employeeId) {
        return resourceAllocationRepository.getEmployeeResourceData(employeeId);
    }

    public List<Map<String, Object>> getEmployeeNamesByProjectCode(String projectName) {
        return resourceAllocationRepository.getEmployeeNamesByProjectCode(projectName);
    }

    public List<Map<String, Object>> getClientUtilisation(String year) {
        return resourceAllocationRepository.getClientUtilisation(year);
    }

    public List<ResourceAllocation> getNonBillableData() {
        return resourceAllocationRepository.getNonBillableData();
    }


}

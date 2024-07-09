package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.repository.TechnologyMasterRepository;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.ResourceAllocationDTO;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResAlloc")
public class ResourceAllocationController {

    @Autowired
    public ResourceAllocationService resourceAllocationService;

    @Autowired
    public ResourceAllocationRepository resourceAllocationRepository;

    @GetMapping("/getAll")
    public List<Resources> getAllResourceAllocation() {
        return resourceAllocationService.getAllResourceAllocations();
    }
    @GetMapping("/getAlls")
    public List<ResourceAllocation> getallResourceAllocation(){
        return resourceAllocationService.pdsgetAllResourceAllocations();
    }

    @GetMapping("/byId/{id}")
    public Resources getById(@PathVariable Integer id) {
        return resourceAllocationService.getById(id.longValue());
    }

    @GetMapping("/getLocations")
    public List<String> getDistinctLocations() {
        return resourceAllocationService.getDistinctLocations();
    }

    @GetMapping("/getRoles")
    public List<String> getDistinctRoles() {
        return resourceAllocationService.getDistinctRoles();
    }

    @GetMapping("/bench-resources-count")
    public Map<String, Long> getBenchResourcesMonthlyCount() {
        return resourceAllocationService.getBenchCountByMonth();
    }

    @GetMapping("/filter")
    public List<Resources> filterResourceAllocations(@RequestParam(required = false) List<String> locations,
                                                     @RequestParam(required = false) List<String> skills,
                                                     @RequestParam(required = false) List<String> availability,
                                                     @RequestParam(required = false) List<String> techGroups,
                                                     @RequestParam(required = false) List<String> roles,
                                                     @RequestParam(required = false) List<String> domain,
                                                     @RequestParam(required = false) Integer availForeCastWeeks,
                                                     @RequestParam(required = false) List<Integer> yearsOfExp) {
        ResourceAllocFilters filterRequest = new ResourceAllocFilters();
        filterRequest.setAvailability(availability);
        filterRequest.setLocations(locations);
        filterRequest.setSkills(skills);
        filterRequest.setRoles(roles);
        filterRequest.setTechGroups(techGroups);
        filterRequest.setDomain(domain);
        filterRequest.setAvailForeCastWeeks(availForeCastWeeks);
        filterRequest.setYearsOfExp(yearsOfExp);
        return resourceAllocationService.filterResourceAllocations(filterRequest);
    }

    @PostMapping("/filter")
    public List<Resources> filterResourceAllocations(@RequestBody ResourceAllocFilters filterRequest) {
        return resourceAllocationService.filterResourceAllocations(filterRequest);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, ?> requestBody){
        return resourceAllocationService.updateStatus(id, requestBody);
    }

    public static Date convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
        LocalDate startdate = LocalDate.parse(dateString, formatter);

        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime startDateZoned = startdate.atStartOfDay(utcZone);

        return java.sql.Date.from(startDateZoned.toInstant());

    }


    @PostMapping("/add")
    public ResponseEntity<?> add (@RequestBody ResourceAllocation resourceAllocation){
        try{
            return ResponseEntity.ok(resourceAllocationService.add(resourceAllocation));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/getCounts")
    public ResponseEntity<?> get (){
        try{
            Map<String,Long> map=new HashMap<>();
            map=resourceAllocationService.getCounts();
            if(map.isEmpty())
                return ResponseEntity.ok("No Data Found");
            else
                return ResponseEntity.ok(map);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }



    @PutMapping("/update")
    public ResponseEntity<ResourceAllocation> updateResourceAllocation(@RequestBody ResourceAllocation updatedAllocation) {
        ResourceAllocation allocation = resourceAllocationService.updateResourceAllocation(updatedAllocation);
        if (allocation != null) {
            return ResponseEntity.ok(allocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("getEmployees")
    public List <Long> getEmployeesData() {
        return resourceAllocationService.getEmployeesData();
    }
    @GetMapping("getEmployeesAllocated")
    public List <Long> getEmployeesAllocatedData() {
        return resourceAllocationService.getEmployeesAllocatedData();
    }

    @GetMapping("getResourceDataList/{startDate}/{endDate}")
    public List <ResourceAllocation> getResourceDataList(@PathVariable String startDate, @PathVariable String endDate) {
        return resourceAllocationService.getResourceDataList(startDate, endDate);
    }

    @GetMapping("getEndingResources/{startDate}/{endDate}")
    public  ResponseEntity<?> getEnding(@PathVariable(name = "startDate") String startDate,@PathVariable(name = "endDate") String endDate){
        try{
            Integer ending=resourceAllocationService.getEnding(startDate,endDate);
            return ResponseEntity.ok(ending);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/get/{year}")
    public List<Map<String,Integer>> get(@PathVariable ("year")String year){
        return resourceAllocationService.get(year);
    }

    @Autowired
    TechnologyMasterRepository trepo;
    @GetMapping("/getAlltechids")
    public List<Integer> getTechIds(){
        return trepo.findAllTechId();
    }

    @GetMapping("/getData/{month}/{year}")
    public List<ResourceAllocation> getDataBasedOnMonthAndYear(@PathVariable String month, @PathVariable String year) {
        return resourceAllocationService.getDataBasedOnMonthAndYear(month, year);
    }
    @GetMapping("getDataOnYear/{year}")
    public List<ResourceAllocation> getDataBasedOnYear(@PathVariable String year) {
        return resourceAllocationService.getDataBasedOnYear(year);
    }

    @GetMapping("getAvailableResources")
    public List<ResourceAllocation> getAvailableResources() {
        return resourceAllocationService.getAvailableResources();
    }
    @GetMapping("getAllocatedResources")
    public List<ResourceAllocation> getAllocatedResources() {
        return resourceAllocationService.getAllocatedResources();
    }

    @GetMapping("getDataOftheYear")
    public List<ResourceAllocationDTO> getDataOfTheYear() {
        return resourceAllocationService.getDataOfTheYear();
    }

    @GetMapping("getDataForClientName/{clientName}/{projectName}")
    public List<ResourceAllocation> getDataForClientName(@PathVariable ("clientName") String clientName, @PathVariable ("projectName") String projectName) {
        return resourceAllocationService.getDataForClientName(clientName, projectName);
    }


    @GetMapping("getProjectList/{clientCode}")
    public List<String> getDataForProjects(@PathVariable String clientCode) {
        return resourceAllocationService.getDataForProjects(clientCode);
    }


    @GetMapping("getNumberOfClients")
    public List<Map<String, Object>> getNumberOfClients() {
        return resourceAllocationService.getNumberOfClients();
    }

    @GetMapping("getEmployeeId")
    public List<String> getEmployeeId() {
        return resourceAllocationService.getEmployeeId();
    }

    @GetMapping("getEmployeeData/{employeeId}")
    public List<ResourceAllocation> getEmployeeResourceData(@PathVariable String employeeId) {
        return resourceAllocationService.getEmployeeResourceData(employeeId);
    }

    @GetMapping("getEmployeeNamesByProjectCode/{projectName}")
    public List<Map<String, Object>> getEmployeeNamesByProjectCode(@PathVariable String projectName) {
        return resourceAllocationService.getEmployeeNamesByProjectCode(projectName);
    }

    @GetMapping("getClientUtilisation/{year}")
    public List<Map<String, Object>> getClientUtilisation(@PathVariable String year) {
        return resourceAllocationService.getClientUtilisation(year);
    }

    @GetMapping("getNonBillableData")
    public List<ResourceAllocation> getgetNonBillableData() {
        return resourceAllocationService.getNonBillableData();
    }


}

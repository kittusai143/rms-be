package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

}

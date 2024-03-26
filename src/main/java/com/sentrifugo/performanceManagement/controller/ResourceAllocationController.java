package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResAlloc")
public class ResourceAllocationController {

    @Autowired
    public ResourceAllocationService resourceAllocationService;

    @GetMapping("/getAll")
    public List<ResourceAllocation> getallResourceAllocation(){
        return resourceAllocationService.getAllResourceAllocations();
    }

    @PostMapping("/filter")
    public List<ResourceAllocation> filterResourceAllocations(@RequestBody ResourceAllocFilters filterRequest) {
        List<String> locations = filterRequest.getLocations();
        List<String> skills = filterRequest.getSkills();
        List<String> billabilities = filterRequest.getBillabilities();
        List<String> roles = filterRequest.getRoles();
        List<String> techGroups = filterRequest.getTechGroup();
        List<String> Domain = filterRequest.getDomain();
        Integer yearsofExp = filterRequest.getYearsOfExp();


        // three filters are provided
        if (locations != null && !locations.isEmpty() && skills != null && !skills.isEmpty() && billabilities != null && !billabilities.isEmpty()) {
            return resourceAllocationService.findByLocationAndSkillsAndBillability(locations, skills, billabilities);
        }
        // locations and skills
        else if (locations != null && !locations.isEmpty() && skills != null && !skills.isEmpty()) {
            return resourceAllocationService.findByLocationAndSkills(locations, skills);
        }
        // only locations and Billability
        else if (locations != null && !locations.isEmpty() && billabilities != null && !billabilities.isEmpty()) {
            return resourceAllocationService.findByLocationAndBillability(locations, billabilities);
        }
        //only skills and Billability
        else if (skills != null && !skills.isEmpty() && billabilities != null && !billabilities.isEmpty()) {
            return resourceAllocationService.findBySkillsAndBillability(skills, billabilities);
        }
        //only locations
        else if (locations != null && !locations.isEmpty()) {
            return resourceAllocationService.findByLocation(locations);
        }
        // only skills
        else if (skills != null && !skills.isEmpty()) {
            return resourceAllocationService.findBySkills(skills);
        }
        //only Billability
        else if (billabilities != null && !billabilities.isEmpty()) {
            return resourceAllocationService.findByBillability(billabilities);
        }
        // no filters
        else {
            return resourceAllocationService.getAllResourceAllocations();
        }
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ResourceAllocation> updateResourceAllocation(@PathVariable Long id, @RequestBody Map<String,?> updatedAllocation) {
//        ResourceAllocation allocation = resourceAllocationService.updateResourceAllocation(id, updatedAllocation);
//        if (allocation != null) {
//            return ResponseEntity.ok(allocation);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}

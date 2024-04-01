package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResAlloc")
public class ResourceAllocationController {

    @Autowired
    public ResourceAllocationService resourceAllocationService;

    @GetMapping("/getAll")
    public List<Resources> getAllResourceAllocation(){ return resourceAllocationService.getAllResourceAllocations(); }

    @GetMapping("/getLocations")
    public List<String> getDistinctLocations(){ return resourceAllocationService.getDistinctLocations(); }

    @GetMapping("/getRoles")
    public List<String> getDistinctRoles(){ return resourceAllocationService.getDistinctRoles(); }

    @GetMapping("/filter")
    public List<ResourceAllocation> filterResourceAllocations(@RequestParam(required = false) List<String> locations,
                                                              @RequestParam(required = false) List<String> skills,
                                                              @RequestParam(required = false) List<String> billabilities,
                                                              @RequestParam(required = false) List<String> techgroups,
                                                              @RequestParam(required = false) List<String> roles,
                                                              @RequestParam(required = false) List<String> domain,
                                                              @RequestParam(required = false) List<Integer> yearsOfExp) {
        ResourceAllocFilters filterRequest = new ResourceAllocFilters();
        filterRequest.setBillabilities(billabilities);
        filterRequest.setLocations(locations);
        filterRequest.setSkills(skills);
        filterRequest.setRoles(roles);
        filterRequest.setTechgroups(techgroups);
        filterRequest.setDomain(domain);
        filterRequest.setYearsOfExp(yearsOfExp);
        return resourceAllocationService.filterResourceAllocations(filterRequest);
    }

    @PostMapping("/filter")
    public List<ResourceAllocation> filterResourceAllocations(@RequestBody ResourceAllocFilters filterRequest) {
       return resourceAllocationService.filterResourceAllocations(filterRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResourceAllocation> updateResourceAllocation(@PathVariable Long id, @RequestBody Map<String,?> updatedAllocation) throws ParseException {
        ResourceAllocation allocation = resourceAllocationService.updateResourceAllocation(id, updatedAllocation);
        if (allocation != null) {
            return ResponseEntity.ok(allocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

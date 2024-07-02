package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.UtilizationService;
import com.sentrifugo.performanceManagement.vo.UtilizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("util")
public class UtilizationController {
    @Autowired
    private UtilizationService utilizationService;

    @PostMapping("filter")
    public ResponseEntity<?> filter(@RequestBody UtilizationFilter utilizationFilter){
        try{
            return ResponseEntity.ok(utilizationService.filter(utilizationFilter.getSubsidiaries(),utilizationFilter.getClients(),utilizationFilter.getProjects(),utilizationFilter.getYear(),utilizationFilter.getQuarter()));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("getProjectList")
    public List<String> getMultipleDataForProjects(@RequestBody List<String> clientCodes) {
        return utilizationService.getMultipleDataForProjects(clientCodes);
    }
    @PostMapping("getDataForClientNames")
    public List<ResourceAllocation> getDataForClientNames(@RequestBody UtilizationFilter utilizationFilter) {
        return utilizationService.getDataForClientNames(utilizationFilter.getClients(), utilizationFilter.getProjects());
    }

    @PostMapping("getEmployeeData/{employeeId}")
    public ResponseEntity<?> getEmployeeResourceData(@PathVariable String employeeId,@RequestBody UtilizationFilter utilizationFilter) {
        try {
            return ResponseEntity.ok(utilizationService.getEmployeeResourceDataWithFilters(employeeId,utilizationFilter));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}

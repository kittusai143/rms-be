package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResAlloc")
public class ResourceAllocationController {

    @Autowired
    public ResourceAllocationService resourceAllocationService;

    @Autowired
    public ResourceAllocationRepository resourceAllocationRepository;

    @GetMapping("/getAll")
    public List<Resources> getAllResourceAllocation(){ return resourceAllocationService.getAllResourceAllocations(); }


    @GetMapping("/byId/{id}")
    public Resources getById(@PathVariable long id){
        return resourceAllocationService.getById(id);
    }

    @GetMapping("/getLocations")
    public List<String> getDistinctLocations(){ return resourceAllocationService.getDistinctLocations(); }

    @GetMapping("/getRoles")
    public List<String> getDistinctRoles(){ return resourceAllocationService.getDistinctRoles(); }

    @GetMapping("/filter")
    public List<Resources> filterResourceAllocations(@RequestParam(required = false) List<String> locations,
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
    public List<Resources> filterResourceAllocations(@RequestBody ResourceAllocFilters filterRequest) {
       return resourceAllocationService.filterResourceAllocations(filterRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResourceAllocation> updateResourceAllocation(@PathVariable Long id, @RequestBody Map<String,?> updatedAllocation) throws ParseException {
        Optional<ResourceAllocation> optionalAllocation = resourceAllocationRepository.findById(id);
        if (optionalAllocation.isPresent()) {
            ResourceAllocation allocation = optionalAllocation.get();

            allocation.setVendorId((String) updatedAllocation.get("vendorId"));
            allocation.setConsultantId((String) updatedAllocation.get("consultantId"));
            allocation.setSowID((String) updatedAllocation.get("sowID"));
            allocation.setRole((String) updatedAllocation.get("role"));
            allocation.setClientCode((String) updatedAllocation.get("clientCode"));
            allocation.setProjectCode((String) updatedAllocation.get("projectCode"));

            if((String) updatedAllocation.get("billingStartDate")!=null && (String) updatedAllocation.get("billingEndDate") !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                java.util.Date billingStartDate = sdf.parse((String) updatedAllocation.get("startDate"));
                java.util.Date billingEndDate = sdf.parse((String) updatedAllocation.get("endDate"));
                allocation.setBillingStartDate(billingStartDate);
                allocation.setBillingEndDate(billingEndDate);
            }

            allocation.setBillability((String) updatedAllocation.get("billability"));
            allocation.setLocation((String) updatedAllocation.get("location"));
            allocation.setClientTimesheetAccess((String) updatedAllocation.get("clientTimesheetAccess"));
            allocation.setPartnerEmailID((String) updatedAllocation.get("partnerEmailID"));
            allocation.setClientEmailID((String) updatedAllocation.get("clientEmailID"));
            allocation.setYubikey((String) updatedAllocation.get("yubikey"));
            allocation.setYubikeySno((String) updatedAllocation.get("yubikeySno"));
            allocation.setContactNumber((String) updatedAllocation.get("contactNumber"));
            allocation.setGender((String) updatedAllocation.get("gender"));
            allocation.setSkillset1((String) updatedAllocation.get("skillset1"));
            allocation.setSkillset2((String) updatedAllocation.get("skillset2"));
            allocation.setTraining((String) updatedAllocation.get("training"));
            allocation.setCertifications((String) updatedAllocation.get("certifications"));
            allocation.setTechnologydivision((String) updatedAllocation.get("technologydivision"));
            allocation.setAwards((String) updatedAllocation.get("awards"));
            allocation.setAudit((String) updatedAllocation.get("audit"));
            allocation.setAllocationStatus((String) updatedAllocation.get("allocationStatus"));
            allocation.setTechMId((Integer) updatedAllocation.get("techMId"));
            allocation.setYearsOfExp((Integer) updatedAllocation.get("yearsOfExp"));

            return ResponseEntity.ok(resourceAllocationService.updateResourceAllocation(allocation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

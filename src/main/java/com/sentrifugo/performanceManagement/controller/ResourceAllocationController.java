package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    public List<Resources> getAllResourceAllocation() {
        return resourceAllocationService.getAllResourceAllocations();
    }


    @GetMapping("/byId/{id}")
    public Resources getById(@PathVariable long id) {
        return resourceAllocationService.getById(id);
    }

    @GetMapping("/getLocations")
    public List<String> getDistinctLocations() {
        return resourceAllocationService.getDistinctLocations();
    }

    @GetMapping("/getRoles")
    public List<String> getDistinctRoles() {
        return resourceAllocationService.getDistinctRoles();
    }

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
    public static Date convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
        LocalDate startdate = LocalDate.parse(dateString, formatter);

        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime startDateZoned = startdate.atStartOfDay(utcZone);

        return java.sql.Date.from(startDateZoned.toInstant());

    }

    @PostMapping("/create")
    public ResponseEntity<String> addResourceAllocation(@RequestBody List<Map<String, ?>> list) {

        try {
            for( Map<String, ?> requestBody : list){
                ResourceAllocation resourceAllocation = new ResourceAllocation();
                if((String) requestBody.get("SilId") !=null){
                    resourceAllocation.setSilId((String) requestBody.get("SilId"));
                }
                if((String) requestBody.get("Name") !=null){
                    resourceAllocation.setName((String) requestBody.get("Name"));
                }
                if((String) requestBody.get("Role") !=null){
                    resourceAllocation.setRole((String) requestBody.get("Role"));
                }
                if((String) requestBody.get("EmployeeType") !=null){
                    resourceAllocation.setEmployeeType((String) requestBody.get("EmployeeType"));
                }
                if((String) requestBody.get("DOJ") !=null){
                    resourceAllocation.setDoj(convertStringToDate((String) requestBody.get("DOJ")));
                }
                if((Number) requestBody.get("YearsOfExp") !=null){
                    Double yearsOfExp = Double.parseDouble(requestBody.get("YearsOfExp").toString());
                    resourceAllocation.setYearsOfExp(yearsOfExp);
                }
                if((String) requestBody.get("Status") !=null){
                    resourceAllocation.setStatus((String) requestBody.get("Status"));
                }
                if((String) requestBody.get("partner") !=null){
                    resourceAllocation.setPartner((String) requestBody.get("partner"));
                }
                if( (String) requestBody.get("ProjectCode")!=null){
                    resourceAllocation.setProjectCode((String) requestBody.get("ProjectCode"));
                }
                if( (String) requestBody.get("ClientCode")!=null){
                    resourceAllocation.setClientCode((String) requestBody.get("ClientCode"));
                }
                if( (String) requestBody.get("ProjectName")!=null){
                    resourceAllocation.setProjectName((String) requestBody.get("ProjectName"));
                }
                if( (String) requestBody.get("ProjectType")!=null){
                    resourceAllocation.setProjectType((String) requestBody.get("ProjectType"));
                }
                if( (String) requestBody.get("AllocationStatus")!=null){
                    resourceAllocation.setAllocationStatus((String) requestBody.get("AllocationStatus"));
                }
                if((String) requestBody.get("StartDate")!=null){
                    resourceAllocation.setProjectstartDate(convertStringToDate((String) requestBody.get("StartDate")));
                }
                if((String) requestBody.get("BillingStartDate")!=null){
                    resourceAllocation.setBillingStartDate(convertStringToDate((String) requestBody.get("BillingStartDate")));
                }
                if((String) requestBody.get("BillingEndDate") !=null){
                    resourceAllocation.setBillingEndDate(convertStringToDate((String) requestBody.get("BillingEndDate")));
                }
                if( (String) requestBody.get("ProjectEndDate")!=null){
                    resourceAllocation.setProjectEndDate(convertStringToDate((String) requestBody.get("ProjectEndDate")));
                }
                if((String) requestBody.get("Billability") !=null){
                    resourceAllocation.setBillability((String) requestBody.get("Billability"));
                }
                if((String) requestBody.get("Location") !=null){
                    resourceAllocation.setLocation((String) requestBody.get("Location"));
                }
                if((String) requestBody.get("ClientTimesheetAccess") !=null){
                    resourceAllocation.setClientTimesheetAccess((String) requestBody.get("ClientTimesheetAccess"));
                }
                if((String) requestBody.get("PartnerEmailID") !=null){
                    resourceAllocation.setPartnerEmailID((String) requestBody.get("PartnerEmailID"));
                }
                if((String) requestBody.get("ClientEmailID") !=null){
                    resourceAllocation.setClientEmailID((String) requestBody.get("ClientEmailID"));
                }
                if((String) requestBody.get("Yubikey") !=null){
                    resourceAllocation.setYubikey((String) requestBody.get("Yubikey"));
                }
                if((Number) requestBody.get("YubikeySno") !=null){
                    resourceAllocation.setYubikeySno(((Number) requestBody.get("YubikeySno")).toString());
                }
                if((Number) requestBody.get("ContactNumber") !=null){
                    resourceAllocation.setContactNumber(((Number) requestBody.get("ContactNumber")).toString());
                }
                if((String) requestBody.get("Gender") !=null){
                    resourceAllocation.setGender((String) requestBody.get("Gender"));
                }
                if((String) requestBody.get("Skillset1") !=null){
                    resourceAllocation.setSkillset1((String) requestBody.get("Skillset1"));
                }
                if((String) requestBody.get("Skillset2") !=null){
                    resourceAllocation.setSkillset2((String) requestBody.get("Skillset2"));
                }
                if((String) requestBody.get("Training") !=null){
                    resourceAllocation.setTraining((String) requestBody.get("Training"));
                }
                if((String) requestBody.get("Certifications") !=null){
                    resourceAllocation.setCertifications((String) requestBody.get("Certifications"));
                }
                if((String) requestBody.get("Awards") !=null){
                    resourceAllocation.setAwards((String) requestBody.get("Awards"));
                }
                if((String) requestBody.get("Audit") !=null){
                    resourceAllocation.setAudit((String) requestBody.get("Audit"));
                }
                if((String) requestBody.get("TechnologyDivision") !=null){
                    resourceAllocation.setTechnologydivision((String) requestBody.get("TechnologyDivision"));
                }

                resourceAllocationService.addResourceAllocation(resourceAllocation);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Resource allocation added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding resource allocation."+e);
        }
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
            Double yearsOfExp = Double.parseDouble(updatedAllocation.get("YearsOfExp").toString());
            allocation.setYearsOfExp(yearsOfExp);

            return ResponseEntity.ok(resourceAllocationService.updateResourceAllocation(allocation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

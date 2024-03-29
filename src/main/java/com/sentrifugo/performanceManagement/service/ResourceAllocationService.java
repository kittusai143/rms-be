package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.ResourceAllocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ResourceAllocationService {

    @Autowired
    public ResourceAllocationRepository resourceAllocationRepository;


    public List<ResourceAllocation> filterResourceAllocations(ResourceAllocFilters criteria) {
        Specification<ResourceAllocation> spec = ResourceAllocSpecification.filterResourceAllocations(criteria);
        return resourceAllocationRepository.findAll(spec);
    }

    public List<ResourceAllocation> getAllResourceAllocations(){
      return resourceAllocationRepository.findAll();
    }

    public ResourceAllocation getById(Long id){ return resourceAllocationRepository.findById(id).get();}

    public ResourceAllocation updateResourceAllocation(Long id, Map<String,?> updatedAllocation) throws ParseException {
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

            return resourceAllocationRepository.save(allocation);
        } else {
            return null;
        }
    }

    public List<String> getDistinctLocations() {
        return resourceAllocationRepository.getDistinctLocations();
    }

    public List<String> getDistinctRoles() {
        return resourceAllocationRepository.getDistinctRoles();
    }
}

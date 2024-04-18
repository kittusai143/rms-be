package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.ResourceAllocSpecification;
import com.sentrifugo.performanceManagement.vo.Resources;
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
    @Autowired
    public ResourceAllocProcessRepository resourceAllocProcessRepository;
    public List<Resources> filterResourceAllocations(ResourceAllocFilters criteria) {
        Specification<ResourceAllocation> spec = ResourceAllocSpecification.filterResourceAllocations(criteria);
        List<ResourceAllocation> resource = resourceAllocationRepository.findAll(spec);
        List<Resources> allresources = getAllResourceAllocations(criteria.getBillabilities());
        List<Resources> response = new ArrayList<Resources>();
        for (ResourceAllocation filtered: resource){
            for (Resources res: allresources){
                if( res.getResource().getAllocationId().equals(filtered.getAllocationId()) ){
                    response.add( res );
                }
            }
        }
        return response;
    }

    public List<Resources> getAllResourceAllocations() {
        List<Object[]> result = resourceAllocationRepository.findResourcesWithActiveProcesses(true);
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

    public List<Resources> getAllResourceAllocations(List<String> billabilities) {
        List<Object[]> result = resourceAllocationRepository.findResourcesWithActiveProcesses(true);
        List<Resources> resourcesList = new ArrayList<>();

        for (Object[] row : result) {
            Long resourceId = ((Number) row[0]).longValue();
            ResourceAllocation resourceAllocation = resourceAllocationRepository.findById(resourceId).orElse(null);

            if (resourceAllocation == null) {continue;}

            String processConcatenated = (String) row[1];
            List<ResourceAllocProcess> processes = parseProcesses(processConcatenated);
            boolean allocated= false;
            if(billabilities == null ||  billabilities.isEmpty() || billabilities.contains("Non Billable") || billabilities.contains("NA") ){
                for(ResourceAllocProcess process : processes){
                    if (Objects.equals(process.getProcessStatus(), "Allocated")) {
                        allocated = true;
                        break;
                    }
                }
            }

            if(!allocated){
                Resources resources = new Resources();
                resources.setResource(resourceAllocation);
                resources.setProcesses(processes);
                resourcesList.add(resources);
            }else{
                 continue;
            }
        }
        return resourcesList;
    }

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

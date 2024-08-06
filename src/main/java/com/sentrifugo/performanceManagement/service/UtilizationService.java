
package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.UtilizationRepository;
import com.sentrifugo.performanceManagement.vo.UtilizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UtilizationService {
    @Autowired
    private UtilizationRepository utilizationRepository;

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public Map<String, Map<String, Double>> calculateQuarterlyPercentage(List<ResourceAllocation> allocations, int year) {
        Map<String, Map<String, Double>> quarterlyPercentages = new HashMap<>();
        for (int quarter = 1; quarter <= 4; quarter++) {
            LocalDate quarterStartDate;
            LocalDate quarterEndDate;
            switch (quarter) {
                case 1:
                    quarterStartDate = LocalDate.of(year, Month.APRIL, 1);
                    quarterEndDate = quarterStartDate.plusMonths(3).minusDays(1);
                    break;
                case 2:
                    quarterStartDate = LocalDate.of(year, Month.JULY, 1);
                    quarterEndDate = quarterStartDate.plusMonths(3).minusDays(1);
                    break;
                case 3:
                    quarterStartDate = LocalDate.of(year, Month.OCTOBER, 1);
                    quarterEndDate = quarterStartDate.plusMonths(3).minusDays(1);
                    break;
                case 4:
                    quarterStartDate = LocalDate.of(year + 1, Month.JANUARY, 1); // The next year
                    quarterEndDate = quarterStartDate.plusMonths(3).minusDays(1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid quarter");
            }

            double totalDaysInQuarter = ChronoUnit.DAYS.between(quarterStartDate, quarterEndDate) + 1;
            double daysOnProject = 0;
            double daysOnBilling = 0;
            double daysOnBench = 0;

            LocalDate currentDate = LocalDate.now();
            if (currentDate.isBefore(quarterStartDate)) {
                continue;
            } else if (currentDate.isBefore(quarterEndDate)) {
                quarterEndDate = currentDate;
            }

            for (ResourceAllocation allocation : allocations) {
                Date projectStartDate = allocation.getProjectstartDate();
                Date projectEndDate = allocation.getProjectEndDate();
                Date billingStartDate = allocation.getBillingStartDate();
                Date billingEndDate = allocation.getBillingEndDate();

                LocalDate java8ProjectStartDate = projectStartDate != null ? projectStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate java8ProjectEndDate = projectEndDate != null ? projectEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate java8BillingStartDate = billingStartDate != null ? billingStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate java8BillingEndDate = billingEndDate != null ? billingEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
//                System.out.println(allocation.getName());
//                System.out.println("ProjectStart"+java8ProjectStartDate);
//                System.out.println("ProjectENd"+java8ProjectEndDate);
//                System.out.println("BillingStart"+java8BillingStartDate);
//                System.out.println("BillingEnd"+java8BillingEndDate);
                totalDaysInQuarter += ChronoUnit.DAYS.between(quarterStartDate, quarterEndDate) + 1;
                if (java8ProjectStartDate != null && java8ProjectEndDate != null) {
                    if (java8ProjectStartDate.isBefore(quarterEndDate) && java8ProjectEndDate.isAfter(quarterStartDate)) {
                        LocalDate effectiveStart = java8ProjectStartDate.isBefore(quarterStartDate) ? quarterStartDate : java8ProjectStartDate;
                        LocalDate effectiveEnd = java8ProjectEndDate.isAfter(quarterEndDate) ? quarterEndDate : java8ProjectEndDate;
                        daysOnProject += ChronoUnit.DAYS.between(effectiveStart, effectiveEnd) + 1;
//                        System.out.println("current days On project"+daysOnProject);
                    }
                }

                if (java8BillingStartDate != null && java8BillingEndDate != null) {
                    if (java8BillingStartDate.isBefore(quarterEndDate) && java8BillingEndDate.isAfter(quarterStartDate)) {
                        LocalDate effectiveStart = java8BillingStartDate.isBefore(quarterStartDate) ? quarterStartDate : java8BillingStartDate;
                        LocalDate effectiveEnd = java8BillingEndDate.isAfter(quarterEndDate) ? quarterEndDate : java8BillingEndDate;
                        daysOnBilling += ChronoUnit.DAYS.between(effectiveStart, effectiveEnd) + 1;
//                        System.out.println("current days of billing"+daysOnBilling);
                    }
                }
            }

            daysOnBench = totalDaysInQuarter - daysOnProject;

//            System.out.println("totaldays"+totalDaysInQuarter);
//            System.out.println("BEnch"+daysOnBench);
//            System.out.println("Project"+daysOnProject);
//            System.out.println("Billing"+daysOnBilling);
            double utilizationPercentage = (daysOnProject / totalDaysInQuarter) * 100;
            double billingPercentage = (daysOnBilling / totalDaysInQuarter) * 100;
            double benchPercentage = (daysOnBench / totalDaysInQuarter) * 100;

            Map<String, Double> quarterData = new HashMap<>();
            quarterData.put("utilization", utilizationPercentage);
            quarterData.put("billing", billingPercentage);
            quarterData.put("bench", benchPercentage);

            quarterlyPercentages.put("Quarter-" + quarter, quarterData);
        }

        return quarterlyPercentages;
    }


    public Map<Month, Map<String, Double>> calculateMonthlyPercentage(List<ResourceAllocation> allocations, Integer year, Integer quarter) {
        Map<Month, Map<String, Double>> monthlyPercentages = new HashMap<>();
        LocalDate quarterStartDate;
        LocalDate quarterEndDate;

        switch (quarter) {
            case 1:
                quarterStartDate = LocalDate.of(year, Month.APRIL, 1);
                break;
            case 2:
                quarterStartDate = LocalDate.of(year, Month.JULY, 1);
                break;
            case 3:
                quarterStartDate = LocalDate.of(year, Month.OCTOBER, 1);
                break;
            case 4:
                quarterStartDate = LocalDate.of(year, Month.JANUARY, 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid quarter");
        }

        quarterEndDate = quarterStartDate.plusMonths(3).minusDays(1);

        LocalDate currentMonthStart = quarterStartDate;
        LocalDate currentMonthEnd = currentMonthStart.withDayOfMonth(currentMonthStart.lengthOfMonth());

        while (!currentMonthStart.isAfter(quarterEndDate)) {
            double totalDaysInMonth = ChronoUnit.DAYS.between(currentMonthStart, currentMonthEnd) + 1;
            double daysOnProject = 0;
            double daysOnBilling = 0;
            double daysOnBench = 0;

            LocalDate currentDate = LocalDate.now();
            if (currentDate.isBefore(currentMonthStart)) {
                // Skip months that haven't started yet
                currentMonthStart = currentMonthStart.plusMonths(1).withDayOfMonth(1);
                currentMonthEnd = currentMonthStart.withDayOfMonth(currentMonthStart.lengthOfMonth());
                continue;
            } else if (currentDate.isBefore(currentMonthEnd)) {
                // Adjust end date to current date if within the month
                currentMonthEnd = currentDate;
            }

            for (ResourceAllocation allocation : allocations) {
                Date projectStartDate = allocation.getProjectstartDate();
                Date projectEndDate = allocation.getProjectEndDate();
                Date billingStartDate = allocation.getBillingStartDate();
                Date billingEndDate = allocation.getBillingEndDate();
                totalDaysInMonth+=ChronoUnit.DAYS.between(currentMonthStart, currentMonthEnd) + 1;
                LocalDate java8ProjectStartDate = projectStartDate != null ? projectStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate java8ProjectEndDate = projectEndDate != null ? projectEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate java8BillingStartDate = billingStartDate != null ? billingStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                LocalDate java8BillingEndDate = billingEndDate != null ? billingEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;



                if (java8ProjectStartDate != null && java8ProjectEndDate != null) {
                    if (java8ProjectStartDate.isBefore(currentMonthEnd) && java8ProjectEndDate.isAfter(currentMonthStart)) {
                        LocalDate effectiveStart = java8ProjectStartDate.isBefore(currentMonthStart) ? currentMonthStart : java8ProjectStartDate;
                        LocalDate effectiveEnd = java8ProjectEndDate.isAfter(currentMonthEnd) ? currentMonthEnd : java8ProjectEndDate;
                        daysOnProject += ChronoUnit.DAYS.between(effectiveStart, effectiveEnd) + 1;
                    }
                }

                if (java8BillingStartDate != null && java8BillingEndDate != null) {
                    if (java8BillingStartDate.isBefore(currentMonthEnd) && java8BillingEndDate.isAfter(currentMonthStart)) {
                        LocalDate effectiveStart = java8BillingStartDate.isBefore(currentMonthStart) ? currentMonthStart : java8BillingStartDate;
                        LocalDate effectiveEnd = java8BillingEndDate.isAfter(currentMonthEnd) ? currentMonthEnd : java8BillingEndDate;
                        daysOnBilling += ChronoUnit.DAYS.between(effectiveStart, effectiveEnd) + 1;
                    }
                }
            }
            daysOnBench = totalDaysInMonth - daysOnProject;

            double utilizationPercentage = (daysOnProject / totalDaysInMonth) * 100;
            double billingPercentage = (daysOnBilling / totalDaysInMonth) * 100;
            double benchPercentage = (daysOnBench / totalDaysInMonth) * 100;

            Map<String, Double> monthData = new HashMap<>();
            monthData.put("utilization", utilizationPercentage);
            monthData.put("billing", billingPercentage);
            monthData.put("bench", benchPercentage);

            monthlyPercentages.put(currentMonthStart.getMonth(), monthData);

            currentMonthStart = currentMonthStart.plusMonths(1).withDayOfMonth(1);
            currentMonthEnd = currentMonthStart.withDayOfMonth(currentMonthStart.lengthOfMonth());
        }

        return monthlyPercentages;
    }

    public Map<String,Object> filter(List<String> subsidiaries,List<String> clients,List<String> projects,Integer year,Integer quarter){
        Map<String,Object> result=new HashMap<>();
        Object percentages;
        List<ResourceAllocation> allocations;
//        List<Map<String,Object>> resultQuery=new ArrayList<>();
//        List<ResourceAllocation> multiple=new ArrayList<>();
        if(subsidiaries.isEmpty()){
            if(clients.isEmpty()){
                if(projects.isEmpty()){
                    allocations= utilizationRepository.findAll();
//                    resultQuery=utilizationRepository.filterMultipleByNone();
                }
                else{
                    allocations= utilizationRepository.filterByProjects(projects);
                }
            }
            else{
                if(projects.isEmpty()){
                    allocations= utilizationRepository.filterByClients(clients);
                }
                else{
                    allocations= utilizationRepository.filterByClientsAndProjects(clients,projects);
                }
            }
        }
        else{
            if(clients.isEmpty()){
                if(projects.isEmpty()){
                    allocations= utilizationRepository.filterBySubsidiaries(subsidiaries);
                }
                else{
                    allocations= utilizationRepository.filterBySubsidiariesAndProjects(subsidiaries,projects);
                }
            }
            else{
                if(projects.isEmpty()){
                    allocations=utilizationRepository.filterBySubsidiariesAndClients(subsidiaries,clients);

                }
                else{
                    allocations= utilizationRepository.filterBySubsidiariesAndClientsAndProjects(subsidiaries,clients,projects);
                }
            }
        }
//        for (Map<String, Object> row : resultQuery) {
//            ResourceAllocation resourceAllocation = new ResourceAllocation();
//            resourceAllocation.setAllocationId((Integer) row.get("AllocationId"));
//            resourceAllocation.setSilId((String) row.get("SilId"));
//            resourceAllocation.setVendorId((String) row.get("VendorID"));
//            resourceAllocation.setConsultantId((String) row.get("ConsultantID"));
//            resourceAllocation.setSowID((String) row.get("SowId"));
//            resourceAllocation.setName((String) row.get("Name"));
//            resourceAllocation.setRole((String) row.get("Role"));
//            resourceAllocation.setEmployeeType((String) row.get("EmployeeType"));
//            resourceAllocation.setDoj(convertToDate(row.get("DOJ")));
//            resourceAllocation.setStatus((String) row.get("Status"));
//            resourceAllocation.setClientCode((String) row.get("ClientCode"));
//            resourceAllocation.setProjectCode((String) row.get("ProjectCode"));
//            resourceAllocation.setBillingStartDate(null);
//            resourceAllocation.setBillingEndDate(null);
//            resourceAllocation.setBillability((String) row.get("Billability"));
//            resourceAllocation.setLocation((String) row.get("Location"));
//            resourceAllocation.setClientTimesheetAccess((String) row.get("ClientTimesheetAccess"));
//            resourceAllocation.setPartnerEmailID((String) row.get("PartnerEmailID"));
//            resourceAllocation.setClientEmailID((String) row.get("ClientEmailID"));
//            resourceAllocation.setYubikey((String) row.get("Yubikey"));
//            resourceAllocation.setYubikeySno((String) row.get("YubikeySno"));
//            resourceAllocation.setContactNumber((String) row.get("ContactNumber"));
//            resourceAllocation.setGender((String) row.get("Gender"));
//            resourceAllocation.setSkillset1((String) row.get("Skillset1"));
//            resourceAllocation.setSkillset2((String) row.get("Skillset2"));
//            resourceAllocation.setTraining((String) row.get("Training"));
//            resourceAllocation.setCertifications((String) row.get("Certifications"));
//            resourceAllocation.setTechnologydivision((String) row.get("TechnologyDivision"));
//            resourceAllocation.setAwards((String) row.get("Awards"));
//            resourceAllocation.setAudit((String) row.get("Audit"));
//            resourceAllocation.setAllocationStatus((String) row.get("AllocationStatus"));
//            resourceAllocation.setTechMId((Integer) row.get("TechId"));
//            resourceAllocation.setYearsofExp((Double) row.get("YearsOfExp"));
//            resourceAllocation.setProjectName((String) row.get("ProjectName"));
//            resourceAllocation.setProjectType((String) row.get("ProjectType"));
//            resourceAllocation.setSubsidiary((String) row.get("Subsidiary"));
//            resourceAllocation.setStartDate(convertToDate(row.get("StartDate")));
//            resourceAllocation.setProjectEndDate(convertToDate(row.get("ProjectEndDate")));
//            resourceAllocation.setPartnerEmailID((String) row.get("partner"));
//            multiple.add(resourceAllocation);
//        }
        if(quarter ==null ||quarter ==0){
            percentages= calculateQuarterlyPercentage(allocations,year);
        }
        else{
            percentages= calculateMonthlyPercentage(allocations,year,quarter);
        }
        result.put("list",allocations);
        result.put("percentage",percentages);
//        result.put("multiple",multiple);
        return result;
    }

    public List<String> getMultipleDataForProjects(List<String> clientCodes) {
        return utilizationRepository.getMultipleDataForProjects(clientCodes);
    }
    public List<ResourceAllocation> getDataForClientNames(List<String> clientName, List<String> projectName) {
        List<ResourceAllocation> resourceAllocation = null;
        if(projectName.isEmpty()) {
            resourceAllocation =utilizationRepository.getDataForClientNames(clientName);
            return resourceAllocation;
        }
        else{
            resourceAllocation = utilizationRepository.getDataForClientNamesAndProjectNames(clientName, projectName);
            return resourceAllocation;
        }
    }

    public List<ResourceAllocation> getEmployeeResourceData(String employeeId) {
        return utilizationRepository.getEmployeeResourceData(employeeId);
    }

    public Map<String,Object> getEmployeeResourceDataWithFilters(String employeeId, UtilizationFilter utilizationFilter) {
        Object percentages;
        List<ResourceAllocation> allocations=getEmployeeResourceData(employeeId);
        if(utilizationFilter.getQuarter() ==null ||utilizationFilter.getQuarter() ==0){
            percentages= calculateQuarterlyPercentage(allocations,utilizationFilter.getYear());
        }
        else{
            percentages= calculateMonthlyPercentage(allocations,utilizationFilter.getYear(),utilizationFilter.getQuarter());
        }
        Map<String,Object> result=new HashMap<>();
        result.put("percentage",percentages);
        return  result;
    }



    private Date convertToDate(Object datetime) {
//        System.out.println("date"+datetime);
        if (datetime == null) {
            return null;
        }
        try {
            return new java.sql.Date(dateTimeFormat.parse(datetime.toString()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Object getCounts(List<String> client, List<String> project) {
        Map<String,Integer> result=new HashMap<>();
        if(client.isEmpty()&& project.isEmpty()){
            result.put("available", utilizationRepository.getCountOfAllAvailable());
            result.put("shadow", utilizationRepository.getCountOfAllShadow());
            result.put("allocated", utilizationRepository.getCountOfAllAllocated());

        }
        else if(client.isEmpty()){
            result.put("available", utilizationRepository.getCountOfAvailableByProjects(project));
            result.put("shadow", utilizationRepository.getCountOfShadowByProjects(project));
            result.put("allocated", utilizationRepository.getCountOfAllocatedByProjects(project));
        }
        else if(project.isEmpty()){
            result.put("available", utilizationRepository.getCountOfAvailableByClients(client));
            result.put("shadow", utilizationRepository.getCountOfShadowByClients(client));
            result.put("allocated", utilizationRepository.getCountOfAllocatedByClients(client));
        }
        else{
            result.put("available", utilizationRepository.getCountOfAvailableByClientsAndProjects(project,client));
            result.put("shadow", utilizationRepository.getCountOfShadowByClientsAndProjects(project,client));
            result.put("allocated", utilizationRepository.getCountOfAllocatedByClientsAndProjects(project,client));
        }
        return result;
    }


}

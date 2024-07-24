
package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UtilizationRepository extends JpaRepository<ResourceAllocation,Integer> {
    @Query(value = "SELECT DISTINCT (ra.ProjectName) from resource_allocation ra WHERE ra.ClientCode in :clientCodes", nativeQuery = true)
    List<String> getMultipleDataForProjects(List<String> clientCodes);
    @Query(value = "select * from resource_allocation rat where rat.ProjectName in :projects ",nativeQuery = true)
    List<ResourceAllocation> filterByProjects(List<String> projects);
    @Query(value = "select * from resource_allocation rat where rat.ClientCode in :clients ",nativeQuery = true)
    List<ResourceAllocation> filterByClients(List<String> clients);
    @Query(value = "select * from resource_allocation rat where rat.ClientCode in :clients and rat.ProjectName in :projects",nativeQuery = true)
    List<ResourceAllocation> filterByClientsAndProjects(List<String> clients, List<String> projects);
    @Query(value = "select * from resource_allocation rat where rat.Subsidiary in :subsidiaries ",nativeQuery = true)
    List<ResourceAllocation> filterBySubsidiaries(List<String> subsidiaries);
    @Query(value = "select * from resource_allocation rat where rat.Subsidiary in :subsidiaries and rat.ProjectName in :projects ",nativeQuery = true)
    List<ResourceAllocation> filterBySubsidiariesAndProjects(List<String> subsidiaries, List<String> projects);
    @Query(value = "select * from resource_allocation rat where rat.Subsidiary in :subsidiaries and rat.ClientCode in :clients ",nativeQuery = true)
    List<ResourceAllocation> filterBySubsidiariesAndClients(List<String> subsidiaries, List<String> clients);
    @Query(value = "select * from resource_allocation rat where rat.ClientCode in :clients and rat.Subsidiary in :subsidiaries and rat.ProjectName in :projects",nativeQuery = true)
    List<ResourceAllocation> filterBySubsidiariesAndClientsAndProjects(List<String> subsidiaries, List<String> clients, List<String> projects);
    @Query(value = "select * from resource_allocation ra where ra.ClientCode in :clientName ", nativeQuery = true)
    List<ResourceAllocation> getDataForClientNames(List<String> clientName);
    @Query(value = "select * from resource_allocation ra where ra.ClientCode in :clientName and ra.ProjectName in :projectName ", nativeQuery = true)
    List<ResourceAllocation> getDataForClientNamesAndProjectNames(List<String> clientName, List<String> projectName);

    @Query(value = "SELECT * FROM resource_allocation rat where rat.SilId = :employeeId", nativeQuery = true)
    List<ResourceAllocation> getEmployeeResourceData(String employeeId);



    @Query(value="SELECT rat.AllocationId, rat.SilId, rat.VendorID, rat.ConsultantID, rat.SowId, rat.Name, rat.`Role`, rat.EmployeeType, rat.DOJ, rat.Status, rat.ClientCode,rat.ProjectCode,null as BillingStartDate,null as BillingEndDate, rat.Billability, rat.Location, rat.ClientTimesheetAccess, rat.PartnerEmailID, rat.ClientEmailID, rat.Yubikey,rat.YubikeySno, rat.ContactNumber, rat.Gender, rat.Skillset1, rat.Skillset2, rat.Training, rat.Certifications, rat.TechnologyDivision, rat.Awards, rat.Audit, rat.AllocationStatus, rat.TechId, rat.YearsOfExp, rat.ProjectName, rat.ProjectType, rat.Subsidiary, pa.startdate as StartDate , pa.enddate as ProjectEndDate, rat.partner, rat.Exit_date\n" +
            "FROM resource_allocation rat  join ProjectAllocation pa on pa.ResAllocID =rat.AllocationId   ORDER by rat.SilId   ",nativeQuery = true)
    List<Map<String ,Object>> filterMultipleByNone();
//    @Query(value = "select * from resource_allocation_test rat where rat.ProjectName in :projects ",nativeQuery = true)
//    List<ResourceAllocation> filterMultipleByProjects(List<String> projects);
//    @Query(value = "select * from resource_allocation_test rat where rat.ClientCode in :clients ",nativeQuery = true)
//    List<ResourceAllocation> filterMultipleByClients(List<String> clients);
//    @Query(value = "select * from resource_allocation_test rat where rat.ClientCode in :clients and rat.ProjectName in :projects",nativeQuery = true)
//    List<ResourceAllocation> filterMUltipleByClientsAndProjects(List<String> clients, List<String> projects);
//    @Query(value = "select * from resource_allocation_test rat where rat.Subsidiary in :subsidiaries ",nativeQuery = true)
//    List<ResourceAllocation> filterMultipleBySubsidiaries(List<String> subsidiaries);
//    @Query(value = "select * from resource_allocation_test rat where rat.Subsidiary in :subsidiaries and rat.ProjectName in :projects ",nativeQuery = true)
//    List<ResourceAllocation> filterMultipleBySubsidiariesAndProjects(List<String> subsidiaries, List<String> projects);
//    @Query(value = "select * from resource_allocation_test rat where rat.Subsidiary in :subsidiaries and rat.ClientCode in :clients ",nativeQuery = true)
//    List<ResourceAllocation> filterMultipleBySubsidiariesAndClients(List<String> subsidiaries, List<String> clients);
//    @Query(value = "select * from resource_allocation_test rat where rat.ClientCode in :clients and rat.Subsidiary in :subsidiaries and rat.ProjectName in :projects",nativeQuery = true)
//    List<ResourceAllocation> filterMultipleBySubsidiariesAndClientsAndProjects(List<String> subsidiaries, List<String> clients, List<String> projects);
//    @Query(value = "select * from pmodashboard.resource_allocation_test ra where ra.ClientCode in :clientName ", nativeQuery = true)
//    List<ResourceAllocation> getMultipleDataForClientNames(List<String> clientName);
//    @Query(value = "select * from pmodashboard.resource_allocation_test ra where ra.ClientCode in :clientName and ra.ProjectName in :projectName ", nativeQuery = true)
//    List<ResourceAllocation> getMultipleDataForClientNamesAndProjectNames(List<String> clientName, List<String> projectName);
//
//    @Query(value = "SELECT * FROM pmodashboard.resource_allocation_test rat where rat.SilId = :employeeId", nativeQuery = true)
//    List<ResourceAllocation> getMultipleEmployeeResourceData(String employeeId);


    @Query(value = "SELECT count(*) FROM resource_allocation ra WHERE ra.AllocationStatus = 'Available'",nativeQuery = true)
    Integer getCountOfAllAvailable();
    @Query(value = "SELECT count(*) from resource_allocation rat where rat.Billability = 'Non Billable' AND rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfAllShadow();
    @Query(value = "SELECT count(*) FROM resource_allocation ra WHERE ra.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfAllAllocated();
    @Query(value = "select count(*) from resource_allocation rat where rat.ProjectName in :projects and rat.AllocationStatus = 'Available'",nativeQuery = true)
    Integer getCountOfAvailableByProjects(List<String>  projects);
    @Query(value = "select count(*) from resource_allocation rat where rat.ProjectName in :projects and rat.Billability = 'Non Billable' AND rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfShadowByProjects(List<String>  projects);
    @Query(value = "select count(*) from resource_allocation rat where rat.ProjectName in :projects and rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfAllocatedByProjects(List<String>  projects);
    @Query(value = "select count(*) from resource_allocation rat where rat.ClientCode in :clients and rat.AllocationStatus = 'Available'",nativeQuery = true)
    Integer getCountOfAvailableByClients(List<String> clients);
    @Query(value = "select count(*) from resource_allocation rat where rat.ClientCode in :clients and rat.Billability = 'Non Billable' AND rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfShadowByClients(List<String> clients);
    @Query(value = "select count(*) from resource_allocation rat where rat.ClientCode in :clients and rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfAllocatedByClients(List<String> clients);
    @Query(value = "select count(*) from resource_allocation rat where rat.ProjectName in :projects and rat.ClientCode in :clients and rat.AllocationStatus = 'Available'",nativeQuery = true)
    Integer getCountOfAvailableByClientsAndProjects(List<String> projects,List<String> clients);
    @Query(value = "select count(*) from resource_allocation rat where rat.ProjectName in :projects and rat.ClientCode in :clients and rat.Billability = 'Non Billable' AND rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfShadowByClientsAndProjects(List<String> projects,List<String> clients);
    @Query(value = "select count(*) from resource_allocation rat where rat.ProjectName in :projects and rat.ClientCode in :clients and rat.AllocationStatus = 'Allocated'",nativeQuery = true)
    Integer getCountOfAllocatedByClientsAndProjects(List<String> projects,List<String> clients);




}

package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.ResourceAllocProcessService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocationDTO;
import com.sentrifugo.performanceManagement.vo.Resources;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocation,Long>, JpaSpecificationExecutor<ResourceAllocation> {
  @NotNull Page<ResourceAllocation> findAll(@Nullable Specification<ResourceAllocation> spec, @NonNull Pageable pageable);

  @Query("SELECT DISTINCT ra.location FROM ResourceAllocation ra")
  List<String> getDistinctLocations();
  @Query("SELECT DISTINCT ra.role FROM ResourceAllocation ra")
  List<String> getDistinctRoles();

  @Query(value = "SELECT ra.allocationId, GROUP_CONCAT(rap.Id) AS processes " +
          "FROM resource_allocation ra " +
          "LEFT JOIN ResourceAllocProcess rap ON ra.AllocationId = rap.ResAllocID AND rap.IsActive = :isActive " +
          "WHERE ra.Status != :status "+
          "GROUP BY ra.allocationId", nativeQuery = true)
  List<Object[]> findResourcesWithActiveProcesses(@Param("isActive") boolean isActive, @Param("status") String status);

  @Query(value = "SELECT ra.allocationId, GROUP_CONCAT(rap.Id) AS processes " +
          "FROM resource_allocation ra " +
          "LEFT JOIN ResourceAllocProcess rap ON ra.AllocationId = rap.ResAllocID AND rap.IsActive = :isActive " +
          "WHERE ra.Status != :status "+
          "GROUP BY ra.allocationId having ra.AllocationId =:id", nativeQuery = true)
  Map<String,?> findByIdWithProcesses(@Param("isActive") boolean isActive, @Param("id") long id, @Param("status") String status);

  @Query("SELECT r FROM ResourceAllocation r WHERE r.silId =:silId")
  ResourceAllocation findBySilId(String silId);
  @Query("SELECT r FROM ResourceAllocation r WHERE r.status != :status AND r.doj <= :endDate")
  List<ResourceAllocation> findActiveResources(String status, Date endDate);


  @Query("select COUNT(billability) from ResourceAllocation where billability ='billable' ")
  Long getBillable();
  @Query("select COUNT(billability) from ResourceAllocation where billability='Non Billable' ")
  Long getNonBillable();

  @Query("select COUNT(location) from ResourceAllocation where location = 'Offshore' ")
  Long getOffSite();
  @Query("select COUNT(location) from ResourceAllocation where location='Onshore' ")
  Long getOnSite();

  @Query("select COUNT(employeeType) from ResourceAllocation where employeeType='Employee' ")
  Long getFTE();
  @Query("select COUNT(employeeType) from ResourceAllocation where employeeType='Consultant' ")
  Long getConsult();
  @Query("select COUNT(gender) from ResourceAllocation where gender='Male' ")
  Long getM();
  @Query("select COUNT(gender) from ResourceAllocation where gender='Female' ")
  Long getF();
  @Query("select COUNT(location) from ResourceAllocation where location='offsite-hybrid' ")
  Long getHybrid();

  @Query("select COUNT(location) from ResourceAllocation where location='offsite-remote' ")
  Long getRemote();

  @Query(value = "SELECT COUNT(*) FROM resource_allocation ra WHERE ra.AllocationStatus = 'Available'", nativeQuery = true)
  List<Long> getDetails();
  @Query(value = "SELECT COUNT(*) FROM resource_allocation ra WHERE ra.AllocationStatus = 'Allocated'", nativeQuery = true)
  List<Long> getEmployeesAllocatedData();

  @Query(value = "SELECT * FROM resource_allocation ra WHERE ra.BillingStartDate AND ra.BillingEndDate BETWEEN :startDate AND :endDate", nativeQuery = true)
  List<ResourceAllocation> getResourceDataList(String startDate, String endDate);

  @Query(value = "select count(*) from resource_allocation ra where ra.billingEndDate>=:startDate and ra.billingEndDate<=:endDate",nativeQuery = true)
  Integer getEnding(LocalDate startDate, LocalDate endDate);

  //SELECT
  //    SUM(CASE WHEN MONTH(ra.BillingStartDate) IN (4, 5, 6) AND YEAR(ra.BillingStartDate) = '2023' THEN 1 ELSE 0 END) AS quater1,
  //    SUM(CASE WHEN MONTH(ra.BillingStartDate) IN (7, 8, 9) AND YEAR(ra.BillingStartDate) = '2023' THEN 1 ELSE 0 END) AS quater2,
  //    SUM(CASE WHEN MONTH(ra.BillingStartDate) IN (10, 11, 12) AND YEAR(ra.BillingStartDate) = '2023' THEN 1 ELSE 0 END) AS quater3,
  //    (SELECT COUNT(*) FROM pmodashboard.resource_allocation WHERE MONTH(BillingStartDate) IN (1, 2, 3) AND YEAR(BillingStartDate) = YEAR(DATE_ADD(CONCAT('2023', '-01-01'), INTERVAL 1 YEAR))) AS quater4
  //FROM
  //    pmodashboard.resource_allocation ra
  //WHERE
  //    YEAR(ra.BillingStartDate) = '2023';
  @Query(value = "SELECT SUM(CASE WHEN MONTH(ra.BillingStartDate) IN (4, 5, 6) THEN 1 ELSE 0 END) AS quater1," +
          "SUM(CASE WHEN MONTH(ra.BillingStartDate) IN (7, 8, 9) THEN 1 ELSE 0 END) AS quater2," +
          "SUM(CASE WHEN MONTH(ra.BillingStartDate) IN (10, 11, 12) THEN 1 ELSE 0 END) AS quater3," +
          "(SELECT COUNT(*) FROM resource_allocation WHERE MONTH(BillingStartDate) IN (1, 2, 3) AND YEAR(BillingStartDate) = YEAR(DATE_ADD(CONCAT(:year, '-01-01'), INTERVAL 1 YEAR))) AS quater4 FROM resource_allocation ra WHERE YEAR(ra.BillingStartDate) = :year",nativeQuery = true)
  List<Map<String,Integer>> getDetailsBasedOnYear(String year);

  @Query(value = "SELECT * FROM resource_allocation ra WHERE YEAR(ra.BillingStartDate) = :year AND MONTH(ra.BillingStartDate) = :month", nativeQuery = true)
  List<ResourceAllocation> getDataBasedOnMonthAndYear(String month, String year);

  @Query(value = "SELECT * FROM resource_allocation ra WHERE YEAR (ra.BillingStartDate) = :year", nativeQuery = true)
  List<ResourceAllocation> getDataBasedOnYear(String year);

  @Query(value = "SELECT * FROM resource_allocation ra WHERE ra.AllocationStatus = 'Available'", nativeQuery = true)
  List<ResourceAllocation> getAvailableResources();

  @Query(value = "SELECT * FROM resource_allocation ra WHERE ra.AllocationStatus = 'Allocated'", nativeQuery = true)
  List<ResourceAllocation> getAllocatedResources();

  @Query(value = "SELECT YEAR(ra.BillingStartDate) as year, COUNT(*) as count FROM resource_allocation ra WHERE ra.Billability = 'Billable' GROUP BY YEAR(ra.BillingStartDate)", nativeQuery = true)
  List<ResourceAllocationDTO> getDataOfTheYear();


  @Query(value = "select * from resource_allocation ra where ra.ClientCode = :clientName ", nativeQuery = true)
  List<ResourceAllocation> getDataForClientName(String clientName);

  @Query(value = "select * from resource_allocation ra where ra.ClientCode = :clientName and ra.ProjectName = :projectName ", nativeQuery = true)
  List<ResourceAllocation> getDataForClientNameAndProjectName(String clientName, String projectName);

  @Query(value = "SELECT DISTINCT (ra.ProjectName) from resource_allocation ra WHERE ra.ClientCode = :clientCode", nativeQuery = true)
  List<String> getDataForProjects(String clientCode);

  @Query(value = "SELECT ClientCode, COUNT(*) AS Count FROM resource_allocation GROUP BY ClientCode", nativeQuery = true)
  List<Map<String, Object>> getNumberOfClients();


  @Query(value = "SELECT (rat.SilId) FROM resource_allocation rat", nativeQuery = true)
  List<String> getEmployeeId();


  @Query(value = "SELECT * FROM resource_allocation rat where rat.SilId = :employeeId", nativeQuery = true)
  List<ResourceAllocation> getEmployeeResourceData(String employeeId);

  @Query(value = "SELECT rat.Name, rat.SilId as SilId FROM resource_allocation rat WHERE rat.ProjectName = :projectName", nativeQuery = true)
  List<Map<String, Object>> getEmployeeNamesByProjectCode(String projectName);

  @Query(value = "SELECT distinct rat.ClientCode , rat.Billability , count(*) FROM resource_allocation rat where YEAR(rat.StartDate) = :year group by rat.ClientCode , rat.Billability", nativeQuery = true)
  List<Map<String, Object>> getClientUtilisation(String year);

  @Query(value = "SELECT * from resource_allocation rat where rat.Billability = 'Non Billable' AND rat.AllocationStatus = 'Allocated'", nativeQuery = true)
  List<ResourceAllocation> getNonBillableData();


}

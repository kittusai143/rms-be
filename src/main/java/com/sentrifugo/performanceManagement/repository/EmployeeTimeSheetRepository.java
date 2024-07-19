package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EmployeeTimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeTimeSheetRepository extends JpaRepository<EmployeeTimeSheet,Long> {
    @Query(value = "Select * from Employees_Timesheet rat", nativeQuery = true)
    List<Map<String,Object>> getEmployeeData();

    @Query(value = "SELECT * from Employees_Timesheet et where et.Employee_Name = :employeeName and et.Time_Status = 'Approved'", nativeQuery = true)
    List<Map<String, Object>> getEmployeeName(String employeeName);
    @Query(value = "SELECT " +
            "YEAR(Work_Date) AS Year, " +
            "MONTH(Work_Date) AS Month, " +
            "WEEK(Work_Date, 1) - WEEK(CONCAT(YEAR(Work_Date), '-', MONTH(Work_Date), '-01'), 1) + 1 AS SequentialWeekNumber, " +
            "SUM(Hours) AS TotalApprovedHours " +
            "FROM Employees_Timesheet " +
            "WHERE Time_Status = 'Approved' " +
            "AND Employee_Name = :employeeName " +
            "AND YEAR(Work_Date) = :year " +
            "AND MONTH(Work_Date) = :month " +
            "GROUP BY YEAR(Work_Date), MONTH(Work_Date), SequentialWeekNumber " +
            "ORDER BY YEAR(Work_Date), MONTH(Work_Date), SequentialWeekNumber",
            nativeQuery = true)
    List<Map<String, Object>> getEmployeeWeekData(String year, String month, String employeeName);

    @Query(value = "SELECT Employee_Name,SUM(Hours) AS TotalApprovedHours FROM Employees_Timesheet WHERE Time_Status = 'approved' GROUP BY Employee_Name ORDER BY TotalApprovedHours DESC", nativeQuery = true)
    List<Map<String, Object>> getSumOfEmployee();
}

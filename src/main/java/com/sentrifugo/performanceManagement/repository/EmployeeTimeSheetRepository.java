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

    @Query(value = "SELECT \n" +
            "    ut.userfullname AS ResourceName,\n" +
            "    ut.employeeId,\n" +
            "    bh.Month,\n" +
            "    bh.Year,\n" +
            "    SUM(bh.Week1BillableHours + bh.Week2BillableHours + bh.Week3BillableHours + bh.Week4BillableHours + bh.Week5BillableHours) AS TotalBillableHours\n" +
            "FROM \n" +
            "    Billable_Hours bh\n" +
            "JOIN \n" +
            "    users_timesheet ut \n" +
            "ON \n" +
            "    bh.ResourceName = ut.name_timesheet\n" +
            "GROUP BY \n" +
            "    ut.userfullname,\n" +
            "    ut.employeeId,\n" +
            "    bh.Month,\n" +
            "    bh.Year", nativeQuery = true)
    List<Map<String, Object>> getSumOfEmployee();
}

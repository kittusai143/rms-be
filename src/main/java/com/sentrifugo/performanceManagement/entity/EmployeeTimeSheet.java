package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employees_Timesheet")
public class EmployeeTimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Employee_Name")
    private String employeeName;

    @Column(name = "Work_Date")
    private LocalDate workDate;

    @Column(name = "Time_Status")
    private String status;

    @Column(name = "Hours")
    private String hours;
}

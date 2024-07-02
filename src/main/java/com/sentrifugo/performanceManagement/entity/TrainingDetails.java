package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="training_details")
public class TrainingDetails {

    @Column(name="ID")
    @Id
    private Integer id;
    @Column(name="TrainingMode")
    private String trainingMode;

    @Column(name="TrainingType")
    private String trainingType;

    @Column(name="TrainingName")
    private String trainingName;

    @Column(name="TrainingArea")
    private String trainingArea;

    @Column(name="NominatedTrainees")
    private String nominatedTrainees;

    @Column(name="StartDate")
    private LocalDate startDate;

    @Column(name="EndDate")
    private LocalDate endDate;

    @Column(name="Location")
    private String location;

    @Column(name="Instructor")
    private String instructor;

    @Column(name="NumberOfTrainingHours")
    private Integer numberOfTrainingHours;

    @Column(name="NumberOfNominees")
    private Integer numberOfNominees;

    @Column(name="Remarks")
    private String remarks;
}

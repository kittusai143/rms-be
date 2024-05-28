package com.sentrifugo.performanceManagement.vo;

import lombok.Data;

import java.util.List;
@Data
public class ResourceAllocFilters {

    private List<String> Locations;
    private List<String> Skills;
    private List<String> Availability;
    private List<String> TechGroups;
    private List<String> Roles;
    private List<String> Domain;
    private List<Integer> yearsOfExp;
    private Integer AvailForeCastWeeks;

}

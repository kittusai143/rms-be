package com.sentrifugo.performanceManagement.vo;

import lombok.Data;

import java.util.List;
@Data
public class ResourceAllocFilters {

    private List<String> Locations;
    private List<String> Skills;
    private List<String> Billabilities;
    private List<String> TechGroup;
    private List<String> Roles;
    private List<String> Domain;
    private Integer yearsOfExp;


}

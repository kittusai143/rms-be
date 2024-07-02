package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilizationFilter {
    private List<String> subsidiaries;
    private List<String>  clients;
    private List<String>  projects;
    private Integer year;
    private Integer quarter;
}

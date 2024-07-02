package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerFilter {
    private int manager;
    private  List<String> clients;
    private List<String> projects;
}

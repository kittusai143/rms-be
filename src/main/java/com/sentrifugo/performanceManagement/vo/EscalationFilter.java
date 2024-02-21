package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EscalationFilter {
        private List<String> designations;
        private List<String> departments;
    }



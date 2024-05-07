package com.sentrifugo.performanceManagement.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReadIDs {
    private String role;
    private List<Long> ids;
}

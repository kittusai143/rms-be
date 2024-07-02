package com.sentrifugo.performanceManagement.vo;

import org.springframework.stereotype.Component;

@Component
public interface ResourceAllocationDTO {
    Integer getCount();
    Integer getYear();

}
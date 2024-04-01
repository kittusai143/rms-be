package com.sentrifugo.performanceManagement.vo;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import lombok.Data;

import java.util.List;
@Data
public class Resources {
    private ResourceAllocation resource;
    private List<ResourceAllocProcess> processes;
}

package com.sentrifugo.performanceManagement.vo;


import lombok.Data;

import java.util.List;

@Data
public class DistinctData {
   private List<String> managers;
   private List<String> clients;
   private List<String> projects;


}

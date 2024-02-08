package com.sentrifugo.performanceManagement.vo;

public interface AppraisalDetailsDto {
    String getEmployeeComments();
    String getManagerComments();
    Integer getEmployeeRating(); // Changed int to Integer
    Integer getManagerRating(); // Changed int to Integer
    String getStatus();
}

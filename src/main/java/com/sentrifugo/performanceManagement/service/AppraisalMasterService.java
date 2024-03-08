package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;


@Service
public class AppraisalMasterService {
    @Autowired
    AppraisalMasterRepository appraisalMasterRepository;

    public List<Map<String,Object>> getbydate(Date date){

        // Convert java.util.Date to java.sql.Date with time part set to 00:00:00
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        java.sql.Date mdate = new java.sql.Date(cal.getTimeInMillis());
        return  appraisalMasterRepository.findByDate(mdate);
    }
}
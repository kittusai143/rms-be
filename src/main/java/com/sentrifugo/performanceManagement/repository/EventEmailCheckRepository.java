package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.EmailEventCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventEmailCheckRepository extends JpaRepository<EmailEventCheck,Integer> {

    @Query(value ="SELECT `email_sent_date` FROM email_event_test" ,nativeQuery = true)
    List<Date> getDates();
}

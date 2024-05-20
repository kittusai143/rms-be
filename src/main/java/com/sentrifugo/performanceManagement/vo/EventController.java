package com.sentrifugo.performanceManagement.vo;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.sentrifugo.performanceManagement.repository.UsersRepository;
import com.sentrifugo.performanceManagement.vo.EmailServiceImpl;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@CrossOrigin("*")
@RestController
public class EventController {
    //
    @Autowired
    private EmailServiceImpl email;

    @Autowired
    UsersRepository userRepo;

    @PostMapping("/store-events")
//    @Scheduled(cron = "0 0 20 * * *") // Run every day at 8 PM
    @Scheduled(cron = "0 30 9 * * *") // Run every day at 9:30 AM
    public ResponseEntity<String> sendmails() {
        try {
            // Convert array to list for easier manipulation
            System.out.println("Cron sheduler called");

            Date date=new Date();
//
            List<Map<String,Object>> birthdayUsers;
            birthdayUsers=userRepo.findActiveUsersByBirthday(date);
            System.out.println(birthdayUsers.size());

            List<Map<String,Object>> anniversaryUsers=userRepo.findEmployeesWithAnniversaryByDate(date);
            //   new ArrayList<>();

//            Map<String, Object> dummyAnniversaryUser = new HashMap<>();
//            dummyAnniversaryUser.put("fullName", "Ram Naresh Pogatala");
//            dummyAnniversaryUser.put("email", "saicharannayanagari@gmail.com");
//            dummyAnniversaryUser.put("anniversaryYear", 3); // Adjust anniversary year as needed
//
//            anniversaryUsers.add(dummyAnniversaryUser);


            EmailDetails details = new EmailDetails( new ArrayList<>(), "Happy Birthday", "Birthday Greetings","",0);
            EmailDetails detail = new EmailDetails( new ArrayList<>(), "", "Anniversary Greetings","",0);
//            details.addRecipient("ewwwaintakid@gmail.com");
            for (Map<String, Object> map : birthdayUsers) {
                String fullname=(String) map.get("fullName");
                details.setFullName(fullname);
                details.getRecipient().add((String) map.get("email"));
                System.out.println(details.getRecipient().toString());
                System.out.println(email.sendSimpleMail(details));
                System.out.println("Name: " + fullname);
                System.out.println("Email: " + map.get("email"));
            }
//            for (Map<String,Object> map : anniversaryUsers) {
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                }
//                System.out.println(); // Add a blank line for separation between map entries
//            }

            for ( Map<String,Object> map : anniversaryUsers) {
                String fullname=    (String) map.get("fullname");
                detail.setFullName(fullname);
                detail.getRecipient().add((String) map.get("email"));
                detail.setAnniversaryYear((Integer) map.get("anniversaryYear"));
                email.sendAnniversaryMail(detail);
                System.out.println("Anni Name: " + fullname);
                System.out.println("Email: " + map.get("email"));
                System.out.println("year"+map.get("anniversaryYear"));
            }
            return ResponseEntity.ok("Mails Triggered");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to trigger Mails " + e.getMessage());
        }
    }
}

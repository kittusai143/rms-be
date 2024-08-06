//package com.sentrifugo.performanceManagement.vo;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.sentrifugo.performanceManagement.entity.EmailEventCheck;
//import com.sentrifugo.performanceManagement.repository.EventEmailCheckRepository;
//import com.sentrifugo.performanceManagement.repository.UsersRepository;
//import com.sentrifugo.performanceManagement.vo.EmailServiceImpl;
//import org.apache.logging.log4j.message.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@CrossOrigin(origins = "${custom.frontendUrl}")
//@RestController
//@RequestMapping("Emailresponse")
//public class EventController {
//    //
//    @Autowired
//    private EmailServiceImpl email;
//    @Autowired
//    UsersRepository userRepo;
//    @Autowired
//    EventEmailCheckRepository eventEmailCheckRepository;
//
//    // @PostMapping("/store-events")
////    @Scheduled(cron = "0 0 20 * * *") // Run every day at 8 PM
////
//    @GetMapping("/Email")
////    @Scheduled(cron = "0 0 8 * * *") // Run every day at 8:00 AM
//    public ResponseEntity<String> sendmails() {
//        Date date=new Date();
//        List<Date> lst=eventEmailCheckRepository.getDates();
//        List<String> strlst=new ArrayList<>();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String todayDate = formatter.format(date);
//        for(int i=0;i<lst.size();i++)
//        {
//            strlst.add(lst.get(i).toString().substring(0,10));
//        }
////        System.out.println(lst);
////        System.out.println(strlst);
////        System.out.println(date);
////        System.out.println(todayDate);
//        if(!strlst.contains(todayDate)) {
//            try {
//                // Convert array to list for easier manipulation
//                int j=0;
//                StringBuffer emplist=new StringBuffer("");
////                System.out.println("Cron sheduler called");
//
//
//                List<Map<String, Object>> birthdayUsers;
//                birthdayUsers = userRepo.findActiveUsersByBirthday(date);
////                System.out.println(birthdayUsers.size());
//
//                List<Map<String, Object>> anniversaryUsers = userRepo.findEmployeesWithAnniversaryByDate(date);
//                EmailDetails details = new EmailDetails("", "Happy Birthday", "Birthday Greetings", "", 0, "", "");
//                EmailDetails detail = new EmailDetails("", "", "Anniversary Greetings", "", 0, "", "");
//
//                for (Map<String, Object> map : birthdayUsers) {
//                    if (j == 0) {
//                        emplist.append("BirthDay Mails : ");
//                    }
//                    j++;
//                    String fullname = (String) map.get("fullName");
//                    String rm = (String) map.get("rmEmail");
//
//                    details.setFullName(fullname);
//                    details.setRecipient((String) map.get("email"));
//                    details.setRmEmail(rm);
//                    email.sendSimpleMail(details);
////                    System.out.println("Birthday Name: " + fullname);
////                    System.out.println("Email: " + map.get("email"));
////                    System.out.println(rm);
//                    emplist.append(fullname);
//                    if (j != birthdayUsers.size()) {
//                        emplist.append(",");
//                    }
//                }
//                j=0;
//                for (Map<String, Object> map : anniversaryUsers) {
//                    if(j==0)
//                    {
//                        emplist.append("-");
//                        emplist.append("Anniversary Mails : ");
//                    }
//                    j++;
//                    String fullname = (String) map.get("fullname");
//                    detail.setFullName(fullname);
//                    String rm = (String) map.get("reportingManagermail");
//                    String l2 = (String) map.get("l2email");
//                    detail.setRecipient((String) map.get("email"));
//                    detail.setAnniversaryYear((Integer) map.get("anniversaryYear"));
//                    detail.setRmEmail(rm);
//                    detail.setL2Email(l2);
//                    emplist.append(fullname);
//                    if(j!=anniversaryUsers.size())
//                    {
//                        emplist.append(",");
//                    }
//                email.sendAnniversaryMail(detail);
////                    System.out.println("Anni Name: " + fullname);
////                    System.out.println("Email: " + map.get("email"));
////                    System.out.println("year" + map.get("anniversaryYear"));
////                    System.out.println(rm + "both" + l2);
//                }
//                EmailEventCheck emailEventCheck= new EmailEventCheck();
//                emailEventCheck.setEmailSentDate(date);
//                if(emplist.isEmpty()){
//                    emailEventCheck.setSentTo("No events today");
//                }else {
//                    emailEventCheck.setSentTo(emplist.toString());
//                }
//                eventEmailCheckRepository.save(emailEventCheck);
//                return ResponseEntity.ok("Mails Triggered");
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to trigger Mails " + e.getMessage());
//            }
//        }
//        else
//        {
//
//            return ResponseEntity.ok("Mails Already Triggered");
//        }
//    }
//}
//package com.sentrifugo.performanceManagement.vo;
//
//import com.sentrifugo.performanceManagement.repository.UsersRepository;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
// @RestController
//public class EmailController {
//
//    @Autowired
//    private UsersRepository repo;
//    @Autowired
//    private EmailService emailService;
//
//    // Sending a simple Email
//    @PostMapping("/sendMail")
//    public String sendMail(EmailDetails details){
//        return emailService.sendSimpleMail(details);
//    }
//
//    //Sending email with attachment
//    @PostMapping("/sendMailWithAttachment")
//    public String sendMailWithAttachment(EmailDetails details)
//    {
//
//        try {
//            return emailService.sendMailWithAttachment(details);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}

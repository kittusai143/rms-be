//package com.sentrifugo.performanceManagement.vo;
//
//import com.sentrifugo.performanceManagement.entity.EscalationMaster;
//import com.sentrifugo.performanceManagement.repository.UsersRepository;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
//
//// Annotation
//@RestController
//// Class
//public class EmailController {
//
//    //    public EmailDetails details;
//    @Autowired
//    private UsersRepository repo;
//    @Autowired
//    private EmailService emailService;
//
//    // Sending a simple Email
//    // @PostMapping("/sendMail")
//    public String sendMail(EmailDetails details)
//    {
//
//        return emailService.sendSimpleMail(details);
//    }
//
//    // Sending email with attachment
////    @PostMapping("/sendMailWithAttachment")
////    public String sendMailWithAttachment(EmailDetails details)
////    {
////
////        try {
////            return emailService.sendMailWithAttachment(details);
////        } catch (MessagingException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//    public ResponseEntity<?> send()
//    {
//
//        List<Map<String,String>> map=repo.findbystatus();
//        if(map.isEmpty())
//        {
//            return ResponseEntity.ok("No Email Found for Initialized status");
//        }
//        //  List<String> mesage=null;
//        String recipient;
//        String msgBody;
//        String subject="Regarding Appraisal";
////         msgBody="Hi\n Your Appraisal has been Initiated you can  add commnets On it ";
//        for(Map<String,String> obj:map)
//        {
//            String name=obj.get("name");
//            msgBody="Hi" +name+"\n" + "Your Appraisal has been Initiated you can  add comments On it ";
//            recipient=obj.get("email");
//            System.out.println(recipient);
//            EmailDetails details=new EmailDetails(recipient,msgBody,subject);
////            details.setRecipient(recipient);
////            details.setSubject(subject);
////            details.setMsgBody(msgBody);
//            System.out.println(details.toString());
//            String sop=(sendMail(details));
//            System.out.println(sop);
//        }
//        return ResponseEntity.ok("Emails Sent Successfully");
//    }
//    @GetMapping("/sendMails/{id}")
//    public ResponseEntity<?> sendindivisualstatus(@PathVariable Integer id){
//        System.out.println(id);
//        Map<String,String> deta=repo.findbymanageremployeestatus(id);
//        if(deta.isEmpty()){
//            return ResponseEntity.ok("No details Found");
//        }
//        // return ResponseEntity.ok(details);
//
//        String Ename=deta.get("employeename");
//        String EMpEmail=deta.get("employee_email");
//        //   String msg= empsend(Ename,EMpEmail);
//        String Mname=deta.get("managername");
//        String Memail=deta.get("reporting_manager_email");
//        String status=deta.get("status");
//        String msg=null;
//        String response=null;
//        System.out.println(status);
//
//        if(status.equals("EmployeeSubmitted"))
//        {
//
//            msg= empsend(Ename,EMpEmail,status,Mname);
//            response=managersend(Mname,Memail,Ename,status);
//        }
//        else if(status.equals("ManagerSubmitted"))
//        {
//            System.out.println("cominsdgs");
//            msg= empsend(Ename,EMpEmail,status,Mname);
//            response=managersend(Mname,Memail,Ename,status);
//        }
//
//
//
//        return ResponseEntity.ok(msg+response);
//
//
//    }
//    public String empsend( String Ename, String mail,String status,String Mname){
//        String recipient=mail;
//        String msgBody=null;
//        if(status.equals("EmployeeSubmitted"))
//            msgBody="Hi"+" "+Ename+"\n"+"Your appriasal has been submitted successfully";
//        else if (status.equals("ManagerSubmitted")) {
//            msgBody="Hi"+"  "+Ename+"\n"+"Your Manager " + Mname+ " Has Reviewed Your Appraisal U can check it out";
//        }
//        String subject="Regarding Appraisal Status";
//        EmailDetails detail=new EmailDetails(recipient,msgBody,subject);
//        return   sendMail(detail);
//
//    }
//    public String managersend( String Mname, String mail ,String Ename,String status){
//        String recipient=mail;
//        String msgBody=null;
//        if(status.equals("EmployeeSubmitted"))
//            msgBody="Hi "+"  "+Mname+"\n"+"Your Employee"+"Mr"+Ename+"has submitted his appriasal has been submitted.\n Please Review it.";
//        else if(status.equals("ManagerSubmitted"))
//            msgBody="\"Hi "+Mname+"You have Succesfully Reviwed the"+Ename+  "comments";
//        String subject="Regarding Appraisa Status";
//        EmailDetails detail=new EmailDetails(recipient,msgBody,subject);
//        return   sendMail(detail);
//
//    }
//
//
//
//}
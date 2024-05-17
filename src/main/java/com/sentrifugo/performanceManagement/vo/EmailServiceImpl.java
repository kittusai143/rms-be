//package com.sentrifugo.performanceManagement.vo;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import java.io.File;
//import java.util.Objects;
//
//@Service
//
//
//public class EmailServiceImpl implements EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//    @Autowired
//    private TemplateEngine templateEngine;
//    @Value("${spring.mail.username}") private String sender;
//    public String sendSimpleMail(EmailDetails details) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(details.getRecipient());
//            mimeMessageHelper.setSubject(details.getSubject());
//
//            // Process template
//            Context context = new Context();
//            context.setVariable("message", details.getMsgBody());
//            String emailContent = templateEngine.process("emailTemplate", context);
//
//            mimeMessageHelper.setText(emailContent, true);
//            addInlineImage(mimeMessageHelper);
//            javaMailSender.send(mimeMessage);
//            return "Mail Sent Successfully...";
//        } catch (MessagingException e) {
//            e.printStackTrace(); // Handle exception appropriately
//            return "Error while Sending Mail";
//        }
//    }
//
//    public void addInlineImage(MimeMessageHelper mimeMessageHelper) throws MessagingException {
//        FileSystemResource image = new FileSystemResource(new File("src/main/resources/Images/download.jpg"));
//        mimeMessageHelper.addInline("logo", image);
//    }
//
//    public String sendSimpleMail(EmailDetails details) {
//        try {
//
//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//            mailMessage.setFrom(sender);
//            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMsgBody());
//            mailMessage.setSubject(details.getSubject());
//            javaMailSender.send(mailMessage);
//            return "Mail Sent Successfully...";
//        }
//        catch (Exception e) {
//            return "Error while Sending Mail";
//        }
//    }
//    public String sendMailWithAttachment(EmailDetails details) throws MessagingException {
//        MimeMessage mimeMessage
//                = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//
//        try {
//            mimeMessageHelper
//                    = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(details.getRecipient());
//            mimeMessageHelper.setText(details.getMsgBody());
//            mimeMessageHelper.setSubject(
//                    details.getSubject());
//             FileSystemResource file
//                    = new FileSystemResource(
//                    new File(details.getAttachment()));
//
//            mimeMessageHelper.addAttachment(
//                    Objects.requireNonNull(file.getFilename()), file);
//                            javaMailSender.send(mimeMessage);
//            return "Mail sent Successfully";
//        }
//                    catch (MessagingException e) {
//             return "Error while sending mail!!!";
//        }
//    }
//}
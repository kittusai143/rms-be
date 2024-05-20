package com.sentrifugo.performanceManagement.vo;

import com.sentrifugo.performanceManagement.vo.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class EmailServiceImpl  {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String sender;


    @GetMapping("Sendmail")
    public String sendSimpleMail(@RequestBody EmailDetails details) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setCc("chandrasekhar.mora@sagarsoft.in");
            // Set multiple recipients
            for (String recipient : details.getRecipient()) {
                mimeMessageHelper.addTo(recipient);
            }

            mimeMessageHelper.setSubject(details.getSubject());

            // Process template
            Context context = new Context();
            context.setVariable("message", details.getMsgBody());
            context.setVariable("fullname",details.getFullName());
            String emailContent = templateEngine.process("birthdaytemplate.html", context);

            mimeMessageHelper.setText(emailContent, true);
//            ClassPathResource resource=new ClassPathResource("Images\\bir.jpg");
//            mimeMessageHelper.addInline("imageId",resource);
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception appropriately
            return "Error while Sending Mail";
        }

    }

    public String sendAnniversaryMail(@RequestBody EmailDetails details) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setCc("chandrasekhar.mora@sagarsoft.in");
            // Set multiple recipients
            for (String recipient : details.getRecipient()) {
                mimeMessageHelper.addTo(recipient);
            }

            mimeMessageHelper.setSubject(details.getSubject());

            // Process template
            Context context = new Context();
//            context.setVariable("message", details.getMsgBody());
            context.setVariable("fullname",details.getFullName());
            context.setVariable("count",details.getAnniversaryYear());
            String eeth="";
            if(details.getAnniversaryYear()==1)
                eeth="st";
            else if (details.getAnniversaryYear()==2 )
                eeth="nd";
            else if (details.getAnniversaryYear()==3)
                eeth="rd";
            else eeth="th";
            context.setVariable("eeth",eeth);

            String emailContent = templateEngine.process("AnniversaryTemplate.html", context);

            mimeMessageHelper.setText(emailContent, true);
//            ClassPathResource resource=new ClassPathResource("Images\\bir.jpg");
//            mimeMessageHelper.addInline("imageId",resource);
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception appropriately
            return "Error while Sending Mail";
        }

    }

    //
//    public void addInlineImage(MimeMessageHelper mimeMessageHelper) throws MessagingException {
//        FileSystemResource image = new FileSystemResource("C:\\Users\\user\\Downloads\\Testing\\Testing\\src\\main\\resources\\Images\\bir.png");
//        mimeMessageHelper.addInline("logo", image);
//    }



}

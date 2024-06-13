package com.sentrifugo.performanceManagement.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_event_test")
public class EmailEventCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;
    @Column(name="email_sent_date")
    private Date emailSentDate;
    @Column(name="sent_to")
    private String sentTo;

}
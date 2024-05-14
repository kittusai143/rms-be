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
@Table(name = "notificationHistory_test")
public class NotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "SilId")
    private String silId;

    @Column(name = "ResAllocID")
    private Long resAllocId;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "createddate")
    private Date createdDate;

    @Column(name = "Comment")
    private String comment;

}

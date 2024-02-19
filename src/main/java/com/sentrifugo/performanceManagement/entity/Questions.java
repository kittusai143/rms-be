package com.sentrifugo.performanceManagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Table(schema = "dbo",name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Integer id;

    @Column(name= "config_id")
    private Integer configId;

    @Column(name= "question")
    private String question;

    @Column(name ="parameter")
    private String parameter;

    @Column(name= "description")
    private String description;


}
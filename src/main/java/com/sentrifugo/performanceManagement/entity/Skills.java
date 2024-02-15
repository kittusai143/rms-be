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

@Table(schema = "dbo",name = "skill")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Integer id;

    @Column(name= "config_id")
    private Integer configId;

    @Column(name= "skill")
    private String skill;

    @Column(name= "description")
    private String description;


}
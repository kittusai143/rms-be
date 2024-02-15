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

@Table(schema = "dbo",name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Integer id;

    @Column(name= "config_id")
    private Integer configId;


    @Column(name= "business_unit")
    private String business_unit;

    @Column(name= "department")
    private String department;

    @Column(name= "rating")
    private Integer rating;

    @Column(name= "rating_value")
    private String rating_value;




}

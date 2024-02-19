package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin("*")
@Table(name = "appraisal_config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "config_name")
    private String configName;
    private int createdBy;
    private Date createdDate;
    private int updatedBy;

}

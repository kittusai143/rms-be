package com.sentrifugo.performanceManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "technology_master_test")
public class TechnologyMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TechId")
    private Integer techId;

    @Column(name = "TechGroup")
    private String techGroup;

    @Column(name = "TechSkill")
    private String techSkill;

}

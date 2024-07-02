
package com.sentrifugo.performanceManagement.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_assets")
public class ClientAsset {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String clientId;
        private String accountName;
        private String assetType;
        private String assetId;
        private String model;
        private String assignedEmpId;
        private String empName;
        private LocalDate dateOfIssue;
        private LocalDate dateOfReturn;
        private String comments;

        private String clientCode;
        private String clientContactNumber;
        private String clientEmail;
        private String clientManager;
        private String clientName;
        private Date clientOnboardDate;
        private String clientShortName;
        private String partner;
        private String status;

}

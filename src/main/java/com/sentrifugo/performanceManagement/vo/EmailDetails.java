package com.sentrifugo.performanceManagement.vo;



// Annotations

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Class
public class EmailDetails {


    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;

//    private String name;
//    private String attachment;
}
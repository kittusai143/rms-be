package com.sentrifugo.performanceManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {


    private List<String> recipient;
    private String msgBody;
    private String subject;
    private String fullName;
    private Integer anniversaryYear;

    public void addRecipient(String email) {
        recipient.add(email);
    }
}

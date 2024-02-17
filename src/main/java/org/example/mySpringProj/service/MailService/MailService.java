package org.example.mySpringProj.service.MailService;

public interface MailService {

    void makeRandomNumber();
    String joinEmail(String email);
    void mailSend(String setFrom,String SenderName, String toMail, String title, String content);
    boolean CheckAuthNum(String email,String authNum);
    void DeleteAuthNum(String authNum);
}

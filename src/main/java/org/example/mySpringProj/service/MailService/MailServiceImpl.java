package org.example.mySpringProj.service.MailService;

import org.example.mySpringProj.utils.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    private int authNumber;
    private final RedisUtil redisUtil;

    @Override
    public void makeRandomNumber() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomNumber = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            randomNumber.append(secureRandom.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber.toString());
    }
    @Override
    public String joinEmail(String email) {
        makeRandomNumber();
        String setFrom = "atkheliprac@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = email;
        String SenderName = "YeomApplication";
        String title = "[YeomApplication] 인증 이메일 입니다."; // 이메일 제목
        String content =
                "YeomApplication 인증번호 코드입니다." + 	//html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 [" + authNumber + "] 입니다." +
                        "<br><br>" +
                        "인증번호를 제대로 입력해주세요";
        mailSend(setFrom,SenderName, toMail, title, content);
        redisUtil.setDataExpire(Integer.toString(authNumber),email,1000*60*5);
        return Integer.toString(authNumber);
    }
    @Override
    public void mailSend(String setFrom,String SenderName, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            helper.setFrom(setFrom,SenderName);
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
    }
    @Override
    public boolean CheckAuthNum(String email,String authNum){
        if(redisUtil.getData(authNum)==null){
            return false;
        }
        else if(redisUtil.getData(authNum).equals(email)){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void DeleteAuthNum(String authNum){
        redisUtil.deleteData(authNum);
    }
}
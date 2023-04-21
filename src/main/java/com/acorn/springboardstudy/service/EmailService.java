package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
//@Service는 @Component의 자식어노테이션으로
//여러 Dao 를 실행하고 @Transaction 을 정의할 때 사용, 명시적인 목적도 있음.
@Component
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendMail(EmailDto emailDto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(emailDto.getToUser());//보내는사람 셋팅
        mimeMessageHelper.setSubject(emailDto.getTitle());//제목셋팅
        mimeMessageHelper.setText(emailDto.getMessage(),true);//내용 셋팅 true:html을 보낸다.
        javaMailSender.send(mimeMessage);

    }
}

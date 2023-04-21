package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.EmailDto;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void sendMail() throws MessagingException {
        EmailDto emailDto = new EmailDto();
        emailDto.setToUser("m_okkK@naver.com");
        emailDto.setTitle("이메일보내기 테스트");
        emailDto.setMessage("<h1>잘보내지니?</h1><p>이것저것 써보기</p>");
        emailService.sendMail(emailDto);//예외위임

    }
}
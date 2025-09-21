package com.TTMSGislavedGummiLanka.TTMS_Backend.service.impl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.MailBody;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendSimpleMessage(MailBody mailBody) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(mailBody.to());
            helper.setFrom("ttmsmaildeliverysystem@gmail.com");
            helper.setSubject(mailBody.subject());
            helper.setText(mailBody.text(), true); // Set to true to enable HTML content
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
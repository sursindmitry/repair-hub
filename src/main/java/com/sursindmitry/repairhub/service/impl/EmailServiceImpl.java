package com.sursindmitry.repairhub.service.impl;

import com.sursindmitry.repairhub.service.EmailService;
import com.sursindmitry.repairhub.service.exception.MessagingLogicException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender emailSender;

  private final TemplateEngine templateEngine;

  @Value("${spring.application.base-url}")
  private String baseUrl;

  @Async
  @Override
  public void sendConfirmationEmail(String email, String firstName, String lastName, String token) {
    Context context = new Context();
    context.setVariable("firstName", firstName);
    context.setVariable("lastName", lastName);
    context.setVariable("verificationURL", baseUrl + "/auth/verification?token=" + token);

    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setTo(email);
      helper.setSubject("Verification address");

      String htmlContent = templateEngine.process("email/email-template", context);
      helper.setText(htmlContent, true);

      emailSender.send(message);
    } catch (MessagingException ex) {
      throw new MessagingLogicException("Error while sending confirmation email");
    }
  }
}

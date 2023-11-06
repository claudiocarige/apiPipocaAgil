package br.com.pipocaagil.apipipocaagil.services.emailservice;

import br.com.pipocaagil.apipipocaagil.services.interfaces.EmailSendingService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSendingServiceImpl implements EmailSendingService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UsersService usersService;

    @Override
    public void sendEmail(String toEmail, String subject, String data) throws MessagingException {
        log.info("Sending email to: " + toEmail);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            var emailContent = createEmailBody(toEmail, subject, data);
            helper.setText(emailContent, true);
            javaMailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            log.error("Error sending the email: " + e.getMessage());
            throw new MessagingException("Error sending the email: ", e);
        }
    }

    private String createEmailBody(String email, String subject, String data ) {
        Context context = new Context();

        var emailContent = "";
        if (subject.equals("Bem-vinda(o) à família Pipoca Ágil!")) {
            log.info("Creating Congratulations Email for New Registration!");
            context.setVariable("data", data);
            emailContent = templateEngine.process("emails/welcome.html", context);
        } else if (subject.equals("Recuperação de senha!")) {
            log.info("Creating Email for Password Recovery!");
            context.setVariable("name", usersService.findByUsername(email).getFirstName());
            context.setVariable("data", data);
            emailContent = templateEngine.process("emails/reset-password.html", context);
        }
        return emailContent;
    }
}


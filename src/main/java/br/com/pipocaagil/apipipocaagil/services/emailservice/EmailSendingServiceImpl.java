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
            var message = createEmail(toEmail, subject, data);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error sending the email: " + e.getMessage());
            throw new MessagingException("Error sending the email: ", e);
        }
    }

    private MimeMessage createEmail(String toEmail, String subject, String data ) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        var emailContent = createEmailBody(toEmail, subject, data);
        helper.setText(emailContent, true);
        return helper.getMimeMessage();
    }

    private String createEmailBody(String email, String subject, String data ) {
        Context context = new Context();
        context.setVariable("data", data);
        if (subject.equals("congratulations") || subject.equals("reset-password")) {
            context.setVariable("name", usersService.findByUsername(email).getFirstName());
        }
        var emailContent = templateEngine.process("emails/"+subject+".html", context);
        return emailContent;
    }
}


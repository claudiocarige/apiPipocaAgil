package br.com.pipocaagil.apipipocaagil.services.emailservice;

import br.com.pipocaagil.apipipocaagil.services.EmailSendingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailSendingServiceImpl implements EmailSendingService {

    private final JavaMailSender javaMailSender;
    @Override
    public void sendOrderConfirmationEmail(String toEmail, String subject, String body) {
        log.info("Sending email to: " + toEmail);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);

            String emailContent = "<html><body>" +
                    "<h1 style=\"color: blue;\">Parabéns <span style=\"font-size: 22px;\" >{{name}}</span>!</h1>" +
                    "<h3>Seja bem-vindo(a) à família Pipoca Ágil</h3>" +
                    "<p style=\"font-size: 17px;\">Desejamos a você ótimas leituras.</p>" +
                    "<h4 style=\"font-size: 20px;\">Equipe Pipoca Ágil</h4>" +
                    "</body></html>";

            emailContent = emailContent.replace("{{name}}", body);

            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("Erro ao enviar o e-mail: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar e-mail: ",e);
        }
    }
}

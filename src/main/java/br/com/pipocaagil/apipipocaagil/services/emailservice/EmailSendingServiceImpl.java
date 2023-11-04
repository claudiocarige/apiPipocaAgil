package br.com.pipocaagil.apipipocaagil.services.emailservice;

import br.com.pipocaagil.apipipocaagil.services.interfaces.EmailSendingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailSendingServiceImpl implements EmailSendingService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        log.info("Sending email to: " + toEmail);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            var emailContent = "";
            if (subject.equals("Bem-vindo(a) à família Pipoca Ágil!")) {
                log.info("Creating Congratulations Email for New Registration!");
                emailContent = "<html><body>" +
                        "<h1 style=\"color: blue;\">Parabéns <span style=\"font-size: 22px;\" >{{name}}</span>!</h1>" +
                        "<h3>Seja bem-vindo(a) à família Pipoca Ágil</h3>" +
                        "<p style=\"font-size: 17px;\">Desejamos a você ótimas leituras.</p>" +
                        "<h4 style=\"font-size: 20px;\">Equipe Pipoca Ágil</h4>" +
                        "</body></html>";
            } else if (subject.equals("Recuperação de senha!")) {
                log.info("Creating Email for Password Recovery!");
                emailContent = "<html><body>" +
                        "<h3>Recuperação de senha.</h3>" +
                        "<p style=\"font-size: 17px;\">Login: </p>" +
                        "<p style=\"font-size: 17px;\">Email: <span style=\"font-size: 18px; font-weight: bold;\" >{{email}}</span></p>" +
                        "<p style=\"font-size: 17px;\">Nova senha: <span style=\"font-size: 18px; font-weight: bold;\" >{{name}}</span></p>" +
                        "<h4 style=\"font-size: 20px;\">Equipe de suporte Pipoca Ágil</h4>" +
                        "</body></html>";
            }
                emailContent = emailContent.replace("{{email}}", toEmail);
                emailContent = emailContent.replace("{{name}}", body);

                helper.setText(emailContent, true);

                javaMailSender.send(message);
            } catch(MessagingException e){
                log.error("Error sending the email: " + e.getMessage());
                throw new MessagingException("Error sending the email: ", e);
            }
        }
    }

package lewandowski.demo.Utilities;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@Transactional
public class EmailSenderImpl {

    /*private static final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);
    private final JavaMailSender mailSender;
    @Autowired
    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMessage(String email, String subjectMessage, String contentMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subjectMessage);
        message.setText(contentMessage);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }*/
}
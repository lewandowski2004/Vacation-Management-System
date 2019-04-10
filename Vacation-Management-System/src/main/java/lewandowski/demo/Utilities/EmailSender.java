package lewandowski.demo.Utilities;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class EmailSender {


    public void sendMessage(String to, String subjectMessage, String contentMessage) {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername("radek.lewandowski2004@gmail.com");
        javaMailSender.setPassword("Lewandowski2004");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("radek.lewandowski2004@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subjectMessage);
        simpleMailMessage.setText(contentMessage);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        javaMailSender.send(simpleMailMessage);

    }
}
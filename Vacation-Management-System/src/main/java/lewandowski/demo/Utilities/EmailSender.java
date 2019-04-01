package lewandowski.demo.Utilities;

import javax.mail.MessagingException;

public interface EmailSender {
    void sendEmail(String to, String title, String content) throws MessagingException;
}
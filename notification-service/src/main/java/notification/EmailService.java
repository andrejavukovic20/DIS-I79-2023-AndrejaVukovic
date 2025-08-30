package notification;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {
	  private final JavaMailSender mailSender;

	    public EmailService(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }

	    public void sendDeliveredEmail(String toEmail, String subject, String body) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject(subject);
	        message.setText(body);
	        message.setFrom("andrejatestmejl@gmail.com"); 

	        mailSender.send(message);
	        System.out.println("Email sent to: " + toEmail);
	    }
}

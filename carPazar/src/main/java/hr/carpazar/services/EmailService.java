package hr.carpazar.services;

import hr.carpazar.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    @Async
    public void sendRecoveryEmail(User user, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carpazarmail@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Password Recovery");
        String[] name = user.getFullName().split(" ");
        String emailText = "Hey " + name[0] + ",\n\n"
                + "We received a request to reset your password. Please click on the link below to set a new password:\n"
                + link + "\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
                + "Best regards,\n\n"
                + "Your CarPazar Team";
        message.setText(emailText);
        mailSender.send(message);
    }

    @Async
    public void sendPremiumAccRequest(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carpazarmail@gmail.com");
        message.setTo("carpazarmail@gmail.com");
        message.setSubject("New Premium Profile Request");

        String[] name = user.getFullName().split(" ");
        String userEmail = user.getEmail();
        String emailText = "Hey,\n\n"
                + "A new request for a Premium Profile has been submitted by " + name[0] + " " + name[1] + " (" + userEmail
                + ").\n\n"
                + "Please review the request and take the necessary actions to process it. You can access the user's profile details and request information in the admin panel.\n\n"
                + "Best regards,\n\n"
                + "CarPazar System Notification";
        message.setText(emailText);
        mailSender.send(message);

        message = new SimpleMailMessage();
        message.setFrom("carpazarmail@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Premium User Request Submitted");

        name = user.getFullName().split(" ");
        emailText = "Hello " + name[0] + ",\n\n"
                + "We are delighted to inform you that your request for a Premium Profile on CarPazar has been received and is under review.\n"
                + "Having a Premium Profile enables you to enjoy exclusive features and benefits, enhancing your experience on our platform.\n\n"
                + "We will notify you once your request has been processed. If you have any questions or need further assistance, feel free to reach out to us.\n\n"
                + "Thank you for choosing CarPazar.\n\n"
                + "Best regards,\n\n"
                + "Your CarPazar Team";
        message.setText(emailText);
        mailSender.send(message);
    }
}

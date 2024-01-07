package hr.carpazar.services;
import hr.carpazar.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

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

}

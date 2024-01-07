package hr.carpazar;

import hr.carpazar.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CarPazarApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarPazarApplication.class, args);
	}
}

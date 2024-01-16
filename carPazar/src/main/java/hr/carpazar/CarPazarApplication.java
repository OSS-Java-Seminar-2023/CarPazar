package hr.carpazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@SpringBootApplication
@EnableAsync
public class CarPazarApplication {
	public static void main(String[] args) {
		SpringApplication.run(CarPazarApplication.class, args);
	}
}

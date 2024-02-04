package hr.carpazar.carPazar;

import hr.carpazar.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarPazarApplicationTests {

	@InjectMocks
	private UserController userController;


	@Test
	void contextLoads() {
	}


}

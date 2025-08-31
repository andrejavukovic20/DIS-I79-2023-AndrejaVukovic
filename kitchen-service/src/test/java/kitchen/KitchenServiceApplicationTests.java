package kitchen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		  properties = {
		    "spring.autoconfigure.exclude=" +
		    "org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration," +
		    "org.springframework.boot.autoconfigure.amqp.RabbitAnnotationDrivenConfiguration",
		    "spring.rabbitmq.listener.simple.auto-startup=false",
		    "spring.rabbitmq.listener.direct.auto-startup=false"
		})
@ActiveProfiles("test")
public class KitchenServiceApplicationTests {

	   @Test
	    void contextLoads() {
	    }
}

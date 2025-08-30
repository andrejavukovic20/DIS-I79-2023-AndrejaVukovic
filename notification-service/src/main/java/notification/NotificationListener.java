package notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import notification.config.OrderQueues;
import notification.events.OrderEventDto;
import org.springframework.amqp.rabbit.annotation.Queue;

@Component
public class NotificationListener {

	   private final EmailService emailService;
	   
	   private static final String DEMO_EMAIL = "andrejatestmejl@gmail.com";

	    public NotificationListener(EmailService emailService) {
	        this.emailService = emailService;
	    }

	    @RabbitListener(queuesToDeclare = @Queue(value = OrderQueues.ORDER_DELIVERED, durable = "true"))
	    public void handleDelivered(OrderEventDto delivered) {
	        System.out.println("[NOTIFICATION] Received delivered for orderId=" + delivered.getOrderId()
	                + " corrId=" + delivered.getCorrelationId());

	        String subject = "Your order " + delivered.getOrderId() + " has been delivered!";
	        String body =
	        	    "Hello,\n\n" +
	        	    "Your order has just been delivered.\n\n" +
	        	    "Thank you for choosing us!";

	        emailService.sendDeliveredEmail(DEMO_EMAIL, subject, body);
	        System.out.println("[NOTIFICATION] Email sent to " + DEMO_EMAIL + " for orderId=" + delivered.getOrderId());
	    }
}

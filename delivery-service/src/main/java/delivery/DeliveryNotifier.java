package delivery;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import delivery.events.OrderEventDto;
import delivery.events.OrderQueues;

@Service
public class DeliveryNotifier {
	  private final RabbitTemplate rabbitTemplate;

	  public DeliveryNotifier(RabbitTemplate rabbitTemplate) {
	       this.rabbitTemplate = rabbitTemplate;
	  }

	  public void sendDelivered(OrderEventDto dto) {
	       rabbitTemplate.convertAndSend(OrderQueues.ORDER_DELIVERED, dto);
	       System.out.println("[DELIVERY] Published deliverd event for orderId=" + dto.getOrderId()
           									+ " corrId=" + dto.getCorrelationId());	  
	  }
}

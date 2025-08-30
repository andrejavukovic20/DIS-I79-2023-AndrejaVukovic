package delivery.service;

import delivery.DeliveryNotifier;
import delivery.events.OrderEventDto;
import delivery.events.OrderQueues;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;
public class DeliveryNotifierTest {

	  	@Test
	  	void sendDelivered_sendsToOrderDeliveredQueue() {
	        RabbitTemplate tpl = mock(RabbitTemplate.class);
	        DeliveryNotifier notifier = new DeliveryNotifier(tpl);

	        OrderEventDto dto = new OrderEventDto();
	        dto.setOrderId(10L);

	        notifier.sendDelivered(dto);

	        verify(tpl).convertAndSend(OrderQueues.ORDER_DELIVERED, dto);
	        verifyNoMoreInteractions(tpl);
	    }
}

package kitchen;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import kitchen.events.OrderEventDto;
import kitchen.events.OrderQueues;
import kitchen.store.KitchenEventStore;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.Queue;
@Component
public class OrderListener {

	private final ReadyNotifier readyNotifier;
	private final KitchenEventStore service;
	private final Random random = new Random();
	
	public OrderListener(ReadyNotifier readyNotifier, KitchenEventStore service) {
		this.readyNotifier = readyNotifier;
		this.service = service;
	}
	
	@RabbitListener(queuesToDeclare = @Queue(value = OrderQueues.ORDER_CREATED, durable = "true"))
	public void handleOrderCreated(OrderEventDto event) throws InterruptedException {
		System.out.println("[KITCHEN] Received CREATED for orderId=" + event.getOrderId()
         										+ " corrId=" + event.getCorrelationId());		
		
		service.save(event);
		
		Chef assignedChef = Chef.getRandomChef();
		int preparationTime = random.nextInt(5) + 3;
		Thread.sleep(preparationTime * 1000L);
		
		if (preparationTime > 6) {
			System.out.println("Delay warning: Order " + event.getOrderId() + " took longer than expected.");
		}
		
		OrderEventDto ready = new OrderEventDto();
		ready.setEventId(UUID.randomUUID().toString());
		ready.setOrderId(event.getOrderId());
		ready.setEventType("READY");
		ready.setSource("KITCHEN");
		ready.setChef(assignedChef.name());
		ready.setPrepSeconds(preparationTime);
		ready.setCorrelationId(event.getCorrelationId());
		ready.setOccuredAt(Instant.now());
		
		readyNotifier.sendOrderReady(ready);
		service.save(ready);
	}
}

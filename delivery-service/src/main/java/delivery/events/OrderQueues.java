package delivery.events;

public class OrderQueues {
	 private OrderQueues() {}

	 public static final String ORDER_CREATED = "order_created_queue";
	 public static final String ORDER_READY = "order_ready_queue";
	 public static final String ORDER_DELIVERED = "order_delivered_queue";
}

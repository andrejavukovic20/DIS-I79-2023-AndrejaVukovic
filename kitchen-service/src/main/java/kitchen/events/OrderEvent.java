package kitchen.events;

import kitchen.Chef;

public class OrderEvent {

	private String orderId;
	private String status;
	private Chef chef;
	private int preparationTime;
	
	public OrderEvent() {
		
	}

	public OrderEvent(String orderId, String status, Chef chef, int preparationTime) {
		this.orderId = orderId;
		this.status = status;
		this.chef = chef;
		this.preparationTime = preparationTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Chef getChef() {
		return chef;
	}
	public void setChef(Chef chef) {
		this.chef = chef;
	}
	public int getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}
	
	@Override
	public String toString() {
		return "OrderEvent [orderId=" + orderId + ", status=" + status + ", chef=" + chef + ", preparationTime="
				+ preparationTime + "]";
	}
}

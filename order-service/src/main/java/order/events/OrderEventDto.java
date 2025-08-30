package order.events;

import java.time.Instant;
import java.util.List;

public class OrderEventDto {

	private String eventId;
	private Long orderId;
	private String eventType;
	private String source;
	private String chef;
	private String correlationId;
	private Integer prepSeconds;
	private Instant occuredAt;
	
	private List<Long> missingItemIds;
	
	public OrderEventDto() {}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public Integer getPrepSeconds() {
		return prepSeconds;
	}

	public void setPrepSeconds(Integer prepSeconds) {
		this.prepSeconds = prepSeconds;
	}

	public Instant getOccuredAt() {
		return occuredAt;
	}

	public void setOccuredAt(Instant occuredAt) {
		this.occuredAt = occuredAt;
	}

	public List<Long> getMissingItemIds() {
		return missingItemIds;
	}

	public void setMissingItemIds(List<Long> missingItemIds) {
		this.missingItemIds = missingItemIds;
	}
}

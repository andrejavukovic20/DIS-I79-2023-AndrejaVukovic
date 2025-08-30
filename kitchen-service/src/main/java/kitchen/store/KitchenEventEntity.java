package kitchen.store;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "events")
public class KitchenEventEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(nullable = false, unique = true)
	 private String eventId;
	 
	 @Column(nullable = false)
	 private Long orderId;
	 
	 @Column(nullable = false)
	 private String eventType;  
	 
	 @Column(nullable = false)
	 private String source;  
	 
	 private String chef;

	 private Integer prepSeconds;

	 private String correlationId;
	 
	 @Column(nullable = false)
	 private Instant occuredAt = Instant.now();
	 
	 public KitchenEventEntity( ) {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getPrepSeconds() {
		return prepSeconds;
	}

	public void setPrepSeconds(Integer prepSeconds) {
		this.prepSeconds = prepSeconds;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public Instant getOccuredAt() {
		return occuredAt;
	}

	public void setOccuredAt(Instant occuredAt) {
		this.occuredAt = occuredAt;
	}
}

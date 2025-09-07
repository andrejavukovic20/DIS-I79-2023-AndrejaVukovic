package delivery.service;

import delivery.DeliveryListener;
import delivery.DeliveryNotifier;
import delivery.events.OrderEventDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliveryListenerTest {

	  	@Test
	    @Tag("slow")
	    @Timeout(20) 
	    void handleOrderReady_emitsDelivered_withRealSleep() throws Exception {
	        DeliveryNotifier notifier = mock(DeliveryNotifier.class);
	        DeliveryListener listener = new DeliveryListener(notifier); 

	        OrderEventDto ready = new OrderEventDto();
	        ready.setOrderId(123L);
	        ready.setChef("ANA");
	        ready.setPrepSeconds(5);
	        ready.setCorrelationId("corr-1");

	        long start = System.currentTimeMillis();

	        listener.handleOrderReady(ready);

	        long elapsedMs = System.currentTimeMillis() - start;

	        assertTrue(elapsedMs >= 7_800L, "Expected duration >= 8, was: " + elapsedMs + " ms");

	        ArgumentCaptor<OrderEventDto> cap = ArgumentCaptor.forClass(OrderEventDto.class);
	        verify(notifier).sendDelivered(cap.capture());
	        verifyNoMoreInteractions(notifier);

	        OrderEventDto delivered = cap.getValue();
	        assertEquals("DELIVERED", delivered.getEventType());
	        assertEquals("DELIVERY", delivered.getSource());
	        assertEquals(123L, delivered.getOrderId());
	        assertEquals("corr-1", delivered.getCorrelationId());
	        assertNotNull(delivered.getOccuredAt());
	    }
}

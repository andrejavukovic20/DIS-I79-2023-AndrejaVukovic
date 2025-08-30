package notification.service;

import notification.EmailService;
import notification.NotificationListener;
import notification.events.OrderEventDto;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class NotificationListenerTest {

	    @Test
	    void handleDelivered_sendsEmail_withExpectedSubjectAndBody() {
	        EmailService email = mock(EmailService.class);
	        NotificationListener listener = new NotificationListener(email);

	        OrderEventDto delivered = new OrderEventDto();
	        delivered.setOrderId(42L);
	        delivered.setCorrelationId("corr-xyz");

	        // act
	        listener.handleDelivered(delivered);

	        // expected values po tvojoj implementaciji
	        String expectedTo = "andrejatestmejl@gmail.com";
	        String expectedSubject = "Your order 42 has been delivered!";
	        String expectedBody =
	                "Hello,\n\n" +
	                "Your order has just been delivered.\n\n" +
	                "Thank you for choosing us!";

	        verify(email).sendDeliveredEmail(expectedTo, expectedSubject, expectedBody);
	        verifyNoMoreInteractions(email);
	    }
}

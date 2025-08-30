package order.service;

import order.OrderStatus;
import order.client.MenuClient;
import order.events.OrderEventPublisher;
import order.model.Order;
import order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

	  	private OrderRepository repository;
	    private MenuClient menuClient;
	    private OrderEventPublisher publisher;
	    private OrderService service;

	    @BeforeEach
	    void setUp() {
	        repository = mock(OrderRepository.class);
	        menuClient = mock(MenuClient.class);
	        publisher = mock(OrderEventPublisher.class);
	        service = new OrderService(repository, menuClient, publisher);
	    }

	    // helpers
	    private Order newOrder(List<Long> ids) {
	        Order o = new Order();
	        o.setMenuIds(ids);
	        return o;
	    }

	    // ------- getAll / getById
	    @Test
	    void getAllOrders_returnsList() {
	        when(repository.findAll()).thenReturn(List.of(new Order(), new Order()));
	        var all = service.getAllOrders();
	        assertEquals(2, all.size());
	        verify(repository).findAll();
	    }

	    @Test
	    void getOrderById_found() {
	        var existing = new Order();
	        existing.setId(5L);
	        when(repository.findById(5L)).thenReturn(Optional.of(existing));
	        var res = service.getOrderById(5L);
	        assertEquals(5L, res.getId());
	    }

	    @Test
	    void getOrderById_notFound() {
	        when(repository.findById(99L)).thenReturn(Optional.empty());
	        var ex = assertThrows(RuntimeException.class, () -> service.getOrderById(99L));
	        assertEquals("Item not found", ex.getMessage());
	    }

	    // ------- createOrder (happy path)
	    @Test
	    void createOrder_ok_reserves_menu_emits_created_event() {
	        var input = newOrder(List.of(1L, 2L));
	        when(menuClient.reserveItems(List.of(1L, 2L))).thenReturn(List.of());
	        when(repository.save(any(Order.class))).thenAnswer(inv -> {
	            Order o = inv.getArgument(0);
	            o.setId(42L);
	            return o;
	        });

	        Order saved = service.createOrder(input);

	        assertEquals(42L, saved.getId());
	        assertEquals(OrderStatus.CREATED, saved.getStatus());
	        assertNotNull(saved.getCreatedAt());

	        // spremljen je red
	        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
	        verify(repository, atLeastOnce()).save(captor.capture());
	        assertEquals(OrderStatus.CREATED, captor.getValue().getStatus());

	        // poslat CREATED event
	        verify(publisher).publishOrderCreatedEvent(eq(42L), anyString());
	        verify(publisher, never()).publishOutOfStockEvent(any(), anyList(), anyString());
	    }

	    // ------- createOrder (out of stock)
	    @Test
	    void createOrder_outOfStock_emits_ooS_event_saves_cancelled_then_409() {
	        var input = newOrder(List.of(1L, 2L, 3L));
	        var missing = List.of(2L, 3L);

	        when(menuClient.reserveItems(List.of(1L, 2L, 3L))).thenReturn(missing);
	        when(repository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

	        var ex = assertThrows(ResponseStatusException.class, () -> service.createOrder(input));
	        assertEquals(409, ex.getStatusCode().value());
	        assertTrue(ex.getReason().contains("OUT_OF_STOCK"));

	        // provjeri da je CANCELLED snimljen
	        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
	        verify(repository).save(captor.capture());
	        assertEquals(OrderStatus.CANCELLED, captor.getValue().getStatus());
	        assertNotNull(captor.getValue().getCreatedAt());

	        // poslat OOS event, a ne CREATED
	        verify(publisher).publishOutOfStockEvent(isNull(), eq(missing), anyString());
	        verify(publisher, never()).publishOrderCreatedEvent(anyLong(), anyString());
	    }

	    // ------- updateOrder
	    @Test
	    void updateOrder_replacesMenuIds_andSaves() {
	        Order existing = new Order();
	        existing.setId(7L);
	        existing.setMenuIds(List.of(1L));

	        when(repository.findById(7L)).thenReturn(Optional.of(existing));
	        when(repository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

	        Order incoming = newOrder(List.of(9L, 10L));
	        var updated = service.updateOrder(7L, incoming);

	        assertEquals(List.of(9L, 10L), updated.getMenuIds());
	        verify(repository).save(existing);
	    }

	    // ------- deleteOrder
	    @Test
	    void deleteOrder_ok() {
	        when(repository.existsById(8L)).thenReturn(true);
	        service.deleteOrder(8L);
	        verify(repository).deleteById(8L);
	    }

	    @Test
	    void deleteOrder_notFound() {
	        when(repository.existsById(8L)).thenReturn(false);
	        var ex = assertThrows(RuntimeException.class, () -> service.deleteOrder(8L));
	        assertEquals("Order with ID 8 not found", ex.getMessage());
	        verify(repository, never()).deleteById(anyLong());
	    }

	    // ------- updateOrderStatus
	    @Test
	    void updateOrderStatus_sets_and_saves() {
	        Order existing = new Order();
	        existing.setId(3L);
	        existing.setStatus(OrderStatus.CREATED);
	        existing.setCreatedAt(LocalDateTime.now());

	        when(repository.findById(3L)).thenReturn(Optional.of(existing));
	        when(repository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

	        var res = service.updateOrderStatus(3L, OrderStatus.CANCELLED);

	        assertEquals(OrderStatus.CANCELLED, res.getStatus());
	        verify(repository).save(existing);
	    }
}

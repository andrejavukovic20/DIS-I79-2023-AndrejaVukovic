package kitchen.controller;

import kitchen.repository.KitchenEventRepository;
import kitchen.service.KitchenEventService;
import kitchen.store.KitchenEventEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KitchenEventController.class)
public class KitchenEventControllerWebTest {

	 	@Autowired MockMvc mvc;

	    @MockBean KitchenEventService service;
	    @MockBean KitchenEventRepository repo;

	    @Test
	    void getAllEvents_200() throws Exception {
	        when(service.getAll()).thenReturn(List.of(new KitchenEventEntity()));
	        mvc.perform(get("/kitchen/events"))
	           .andExpect(status().isOk());
	    }

	    @Test
	    void getByOrderId_200() throws Exception {
	        when(repo.findByOrderId(55L)).thenReturn(List.of(new KitchenEventEntity()));
	        mvc.perform(get("/kitchen/events/by-order/55"))
	           .andExpect(status().isOk());
	    }
}

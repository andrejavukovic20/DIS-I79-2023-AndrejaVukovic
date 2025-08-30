package kitchen.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchen.repository.KitchenEventRepository;
import kitchen.service.KitchenEventService;
import kitchen.store.KitchenEventEntity;

@RestController
@RequestMapping("/kitchen/events")
public class KitchenEventController {

	private final KitchenEventService service;
	private final KitchenEventRepository repo;
	
	public KitchenEventController(KitchenEventService service, KitchenEventRepository repo) {
		this.service = service;
		this.repo = repo;
	}
	
	 @GetMapping
	 public List<KitchenEventEntity> getAllEvents() {
	    return service.getAll();
	 }
	 
	 @GetMapping("/by-order/{orderId}")
	 public List<KitchenEventEntity> getByOrderId(@PathVariable Long orderId) {
	     return repo.findByOrderId(orderId);
	 }
}

package kitchen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kitchen.repository.KitchenEventRepository;
import kitchen.store.KitchenEventEntity;

@Service
public class KitchenEventService {

	private final KitchenEventRepository repo;
	
	public KitchenEventService(KitchenEventRepository repo) {
		this.repo = repo;
	}
	
	public List<KitchenEventEntity> getAll(){
		return repo.findAll();
	}
}

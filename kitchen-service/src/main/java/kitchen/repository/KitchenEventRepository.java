package kitchen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchen.store.KitchenEventEntity;

public interface KitchenEventRepository extends JpaRepository<KitchenEventEntity, Long> {
    List<KitchenEventEntity> findByOrderId(Long orderId);
}

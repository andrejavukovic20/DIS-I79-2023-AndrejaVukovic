package order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}

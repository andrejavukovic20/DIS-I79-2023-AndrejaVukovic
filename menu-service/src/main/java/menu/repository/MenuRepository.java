package menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import menu.Category;
import menu.model.Menu;

public interface MenuRepository  extends JpaRepository<Menu, Long>{

	List<Menu> findByCategory(Category category);
	List<Menu> findByAvailableTrue();
	List<Menu> findByIdInAndAvailableTrue(List<Long> ids);
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select m from Menu m where m.id in :ids")
	List<Menu> findAllForUpdate(@Param("ids") List<Long> ids);
}

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;
import domain.StretchOrder;

@Repository
public interface StretchOrderRepository extends JpaRepository<StretchOrder, Integer> {

	@Query("select so from StretchOrder so where so.procession = ?1 order by so.orderNumber")
	Collection<StretchOrder> findByProcession(Procession procession);

}
